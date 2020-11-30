package com.bigbank.dragonsofmugloar;

import com.bigbank.dragonsofmugloar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public int playGame() {

        this.game = gameService.startGame();

        while (!isGameOver) {
            try {
                List<Ad> ads = fetchingAllAds();

                boolean isAttemptSuccess = findingBestTaskAndSolvingAndUpdatingGame(ads);
                System.out.println("--------------------------------");
                System.out.println("Game status after solving task\n" + game);
                System.out.println("--------------------------------");

                //if lives run out, game is over so break out of the loop
                if (game.getLives() == 0) {
                    break;
                }
                boolean isBuyHealthItem = buyingHealthItemsAndUpdatingGame();
                boolean isBuyDragonItem = buyingDragonLevelBoostItemsAndUpdatingGame(isAttemptSuccess);

                if(isBuyDragonItem || isBuyHealthItem) {
                    System.out.println("--------------------------------");
                    System.out.println("Game status after buying item\n" + game);
                    System.out.println("--------------------------------");
                }
            } catch (IllegalStateException exception) {
                System.out.println("There was an issue with the API calls");
                System.out.println("Ending the current Game and Starting a new One");
                isGameOver = true;
            }

        }
        //Set isGameOver to false for new Run
        isGameOver = false;
        return game.getScore();
    }

    private boolean findingBestTaskAndSolvingAndUpdatingGame(List<Ad> ads) {
        Ad ad = gameHelper.selectBestMessageToSolve(ads);
        System.out.println("--------------------------------");
        System.out.println("Selected " + ad);
        System.out.println("--------------------------------");

        SolveMessageResponse solveMessageResponse =
                gameService.solveBestMessage(game.getGameId(), ad.getAdId());
        updateGameAfterSolvingMessage(solveMessageResponse);
        System.out.println("Result of task attempt:");
        System.out.println(solveMessageResponse.getMessage());
        return solveMessageResponse.isSuccess();
    }

    private List<Ad> fetchingAllAds() {
        List<Ad> ads = gameService.fetchAllMessages(game.getGameId());
        System.out.println("Here are the List of Ads that can be solved");
        System.out.println("++++++++++++++++++++++++++++++++++");
        for (Ad a : ads)
            System.out.println(a);
        System.out.println("++++++++++++++++++++++++++++++++++");
        return ads;
    }

    private boolean buyingHealthItemsAndUpdatingGame() {
        List<ShopItem> shopItems = gameService.getShopItems(game.getGameId());
        boolean isBuyItem = false;
        List<String> listOfHealingItemsIDToBuy = gameHelper.getListOfHealingItemsToBuy(shopItems, game);
        for (String itemId : listOfHealingItemsIDToBuy) {
            isBuyItem = true;
            System.out.println("Buying Health Potion...");
            BuyItemResponse buyItemResponse = gameService.buyShopItem(game.getGameId(), itemId);
            updateGameAfterBuyingItem(buyItemResponse);
        }
        return isBuyItem;
    }

    private boolean buyingDragonLevelBoostItemsAndUpdatingGame(boolean isPreviousAttemptSuccess) {
        List<ShopItem> shopItems = gameService.getShopItems(game.getGameId());
        boolean isBuyItem = false;
        List<String> listOfDragonLevelBoostItemsToBuy =
                gameHelper.getListOfDragonLevelBoostItemsToBuy(isPreviousAttemptSuccess, shopItems, game);
        for (String itemId : listOfDragonLevelBoostItemsToBuy) {
            System.out.println("Buying Dragon Booster...");
            isBuyItem = true;
            BuyItemResponse buyItemResponse = gameService.buyShopItem(game.getGameId(), itemId);
            updateGameAfterBuyingItem(buyItemResponse);
        }
        return isBuyItem;
    }

    private void updateGameAfterBuyingItem(BuyItemResponse buyItemResponse) {
        game.setGold(buyItemResponse.getGold());
        game.setLives(buyItemResponse.getLives());
        game.setLevel(buyItemResponse.getLevel());
        game.setTurn(buyItemResponse.getTurn());
    }

    private void updateGameAfterSolvingMessage(SolveMessageResponse solveMessageResponse) {
        game.setLives(solveMessageResponse.getLives());
        game.setGold(solveMessageResponse.getGold());
        game.setScore(solveMessageResponse.getScore());
        game.setHighScore(solveMessageResponse.getHighScore());
        game.setTurn(solveMessageResponse.getTurn());
    }


}
