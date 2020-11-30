package com.bigbank.dragonsOfMugloar.application;

import com.bigbank.dragonsOfMugloar.application.model.*;
import com.bigbank.dragonsOfMugloar.application.service.GameService;
import com.bigbank.dragonsOfMugloar.application.util.GameHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This is a main class which drives the game. This class uses GameService to make API calls
 * and receive the response from it. This class also uses GameHelper to make decisions on which
 * task to solve, whether to buy shop item or not.
 *
 *
 * @author Manish Gupta
 * @version $Id: GameEngine.java 1.0
 * @since 2020-11-28
 */
@Component
public class GameEngine {


    private final GameService gameService;
    private final GameHelper gameHelper;

    @Autowired
    public GameEngine(GameService gameService, GameHelper gameHelper) {
        this.gameService = gameService;
        this.gameHelper = gameHelper;
    }

    private Game game;

    private boolean isGameOver;

    /**
     * A method that kick starts the game.
     *
     * @return final score in the game.
     */
    public int playGame() {
        System.out.println("+++++++++++++++++++++++++++++++++");
        System.out.println("STARTING NEW GAME");
        System.out.println("+++++++++++++++++++++++++++++++++");

        //start the game
        this.game = gameService.startGame();

        //keep solving tasks and buying items until life runs out
        while (!isGameOver) {
            try {
                List<Ad> ads = fetchingAllAds();

                boolean isAttemptSuccess = findingBestTaskAndSolvingAndUpdatingGame(ads);
                System.out.println("--------------------------------");
                System.out.println("Game status after attempting task\n" + game);
                System.out.println("--------------------------------");


                //if lives run out, game is over so break out of the loop
                if (game.getLives() == 0) {
                    break;
                }
                //if only 1 life left, make Healing potion purchase based on gold availability
                if (game.getLives() == 1 ) {
                    buyingHealthItemsAndUpdatingGame();
                }
                //try buying dragon level booster items
                buyingDragonLevelBoostItemsAndUpdatingGame(isAttemptSuccess);

            } catch (IllegalStateException | HttpClientErrorException exception) {
                System.out.println("There was an issue with the API calls");
                System.out.println("Ending the current Game and Starting a new One");
                isGameOver = true;
            }

        }
        //Set isGameOver to false for new Run
        isGameOver = false;

        //Initialize the Maps that stores failure rates and probability score
        // in GameHelper class before starting the new game
        GameHelper.setFailureNumberForEachProbability(new HashMap<>());
        GameHelper.initializeProbabilityScore();

        System.out.println("Game Over\n+++++++++++++++++++++++++++++++++");
        System.out.println("You scored " + game.getScore());
        System.out.println("+++++++++++++++++++++++++++++++++");
        return game.getScore();
    }

    /**
     * find the message that yields maximum score, attempt the message, and
     * update the game field with lives, gold, score, etc.
     *
     * @param ads list of ads that can be attempted.
     * @return a boolean value indicating if the solve attempt was successful or not
     */
    private boolean findingBestTaskAndSolvingAndUpdatingGame(List<Ad> ads) {
        //select best Ad
        Ad ad = gameHelper.selectBestMessageToSolve(ads);
        System.out.println("--------------------------------");
        System.out.println("Selected " + ad);
        System.out.println("--------------------------------");

        //Attempt the ad
        SolveMessageResponse solveMessageResponse =
                gameService.solveBestMessage(game.getGameId(), ad.getAdId());
        updateGameAfterSolvingMessage(solveMessageResponse);
        System.out.println("Result of task attempt:");
        System.out.println(solveMessageResponse.getMessage());

        //if the task attempt failed update the failureNumberForEachProbability Map inside GameHelper class
        boolean isAttemptSuccess = solveMessageResponse.isSuccess();
        if(!isAttemptSuccess) {
            gameHelper.updateProbabilityScore(ad.getProbability());
        }
        return isAttemptSuccess;
    }

    /**
     * find all the Ads that can be attempted.
     *
     * @return list of Ads that can be attempted.
     */
    private List<Ad> fetchingAllAds() {
        List<Ad> ads = gameService.fetchAllMessages(game.getGameId());
        System.out.println("Here are the List of Ads that can be solved");
        System.out.println("++++++++++++++++++++++++++++++++++");
        for (Ad a : ads)
            System.out.println(a);
        System.out.println("++++++++++++++++++++++++++++++++++");
        return ads;
    }

    /**
     * buy Healing potion 2 times if have enough gold
     */
    private void buyingHealthItemsAndUpdatingGame() {
        int timesBought = 0;
        List<ShopItem> shopItems = gameService.getShopItems(game.getGameId());
        ShopItem healingItemToBuy = gameHelper.getHealingItemToBuy(shopItems);
        while(game.getGold() > healingItemToBuy.getCost() && timesBought < 2) {
            System.out.println("Buying Health Potion...");
            BuyItemResponse buyItemResponse = gameService.buyShopItem(game.getGameId(), healingItemToBuy.getId());
            updateGameAfterBuyingItem(buyItemResponse);
            timesBought++;
        }
    }

    /**
     * query the dragon level booster items, buy them if enough gold, and
     * update the game after the purchase.
     *
     * @param isPreviousAttemptSuccess was last attempt to solve task success or not
     */
    private void buyingDragonLevelBoostItemsAndUpdatingGame(boolean isPreviousAttemptSuccess) {
        List<ShopItem> shopItems = gameService.getShopItems(game.getGameId());

        Optional<ShopItem> dragonLevelBoostItemToBuy =
                gameHelper.dragonLevelBoostItemToBuy(isPreviousAttemptSuccess, shopItems, game);

        if(dragonLevelBoostItemToBuy.isPresent()) {
            System.out.println("Buying Dragon Booster...");
            BuyItemResponse buyItemResponse =
                    gameService.buyShopItem(game.getGameId(), dragonLevelBoostItemToBuy.get().getId());
            updateGameAfterBuyingItem(buyItemResponse);
        }
    }

    /**
     * update the game after making a purchase
     *
     * @param buyItemResponse dto response after buying an item.
     */
    private void updateGameAfterBuyingItem(BuyItemResponse buyItemResponse) {
        game.setGold(buyItemResponse.getGold());
        game.setLives(buyItemResponse.getLives());
        game.setLevel(buyItemResponse.getLevel());
        game.setTurn(buyItemResponse.getTurn());
    }

    /**
     * update the game after attempting a task
     *
     * @param solveMessageResponse dto response after solving a task.
     */
    private void updateGameAfterSolvingMessage(SolveMessageResponse solveMessageResponse) {
        game.setLives(solveMessageResponse.getLives());
        game.setGold(solveMessageResponse.getGold());
        game.setScore(solveMessageResponse.getScore());
        game.setHighScore(solveMessageResponse.getHighScore());
        game.setTurn(solveMessageResponse.getTurn());
    }


}
