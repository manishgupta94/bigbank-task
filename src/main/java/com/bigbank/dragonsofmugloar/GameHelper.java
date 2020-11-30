package com.bigbank.dragonsofmugloar;

import com.bigbank.dragonsofmugloar.exception.HealingPotionNotFoundException;
import com.bigbank.dragonsofmugloar.exception.ShopItemNotFoundException;
import com.bigbank.dragonsofmugloar.model.Ad;
import com.bigbank.dragonsofmugloar.model.Game;
import com.bigbank.dragonsofmugloar.model.ShopItem;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GameHelper {

    private static final Map<String, Integer> PROBABILITY_SCORE =  initializeMap();
    private static final int REWARD_FACTOR = 100;
    private static final int EXPIRY_FACTOR = 250;
    private static final int MIN_INT = -100000;

    public Ad selectBestMessageToSolve(List<Ad> adList) {
        Map<Ad, Integer> adScore = new HashMap<>();

        //call calculateScore to calculate score of each Ad and append the Map(Ad, score) to adScore Map
        adList.forEach(ad ->
                adScore.put(ad, calculateScore(ad.getExpiresIn(), ad.getReward(), ad.getProbability()
                )));

        //Finding the Ad with the maximum score by traversing through adScore Map.
        return Collections.max(adScore.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

    }

    public List<String> getListOfHealingItemsToBuy(List<ShopItem> shopItems, Game game) {
        List<String> listOfHealingItemsIDToBuy = new ArrayList<>();
        ShopItem healingShopItem = shopItems.stream()
                .filter(shopItem -> shopItem.getName().contains("Healing potion"))
                .findAny().orElseThrow(HealingPotionNotFoundException::new);
        int expectedGoldAfterPurchase = game.getGold();
        int expectedLivesAfterPurchase = game.getLives();

        while(isToBuyMoreHealthPotion(game.getLives(), healingShopItem, expectedGoldAfterPurchase, expectedLivesAfterPurchase)) {
                listOfHealingItemsIDToBuy.add(healingShopItem.getId());
                expectedGoldAfterPurchase = expectedGoldAfterPurchase - healingShopItem.getCost();
                expectedLivesAfterPurchase++;
            }

        return listOfHealingItemsIDToBuy;
    }

    public List<String> getListOfDragonLevelBoostItemsToBuy(boolean lastTaskSuccess, List<ShopItem> shopItems, Game game) {
        ShopItem healingShopItem = shopItems.stream()
                .filter(shopItem -> shopItem.getName().contains("Healing potion"))
                .findAny().orElseThrow(HealingPotionNotFoundException::new);

        //List to store IDs Dragon Level Booster Item
        List<String> listOfDragonLevelBoostItemsIDToBuy = new ArrayList<>();

        //List to store unique costs in the Dragon Level Booster Items
        List<Integer> uniqueCostOfDragonItems = shopItems.stream()
                .filter(shopItem -> !shopItem.getName().contains("Healing potion"))
                .map(ShopItem::getCost)
                .distinct().sorted().collect(Collectors.toList());

        //Adding Expensive Items to buy List if current gold value is 100 more than expensive item
        //saving 100 for health potion
        int i = uniqueCostOfDragonItems.size() - 1;
        if(game.getGold() > uniqueCostOfDragonItems.get(i) + (2 * healingShopItem.getCost())) {
            ShopItem dragonLevelBoostItem = shopItems.stream()
                    .filter(shopItem -> shopItem.getCost() == uniqueCostOfDragonItems.get(i))
                    .findAny().orElseThrow(ShopItemNotFoundException::new);
            listOfDragonLevelBoostItemsIDToBuy.add(dragonLevelBoostItem.getId());
        }

        //In case last task was failure and have enough gold, buy cheapest item.
        if(!lastTaskSuccess && game.getGold() > uniqueCostOfDragonItems.get(0) + (2 * healingShopItem.getCost())) {
            ShopItem dragonLevelBoostItem = shopItems.stream()
                    .filter(shopItem -> shopItem.getCost() == uniqueCostOfDragonItems.get(0))
                    .findAny().orElseThrow(ShopItemNotFoundException::new);
            listOfDragonLevelBoostItemsIDToBuy.add(dragonLevelBoostItem.getId());
        }
        return listOfDragonLevelBoostItemsIDToBuy;
    }

    private boolean isToBuyMoreHealthPotion(int currentLives,
                                            ShopItem healingShopItem,
                                            int expectedGoldAfterPurchase,
                                            int expectedLivesAfterPurchase) {
        return currentLives < 2 && expectedGoldAfterPurchase > healingShopItem.getCost()
                    && expectedLivesAfterPurchase < 4;
    }


    private int calculateScore(int expiresIn, int reward, String probability) {
        int score;
        if(PROBABILITY_SCORE.containsKey(probability)) {
            score = PROBABILITY_SCORE.get(probability);
        } else {
            //In case, probability is not in the PROBABILITY_SCORE Map, this means task is with corrupt probability
            // name, and non-existent Id. Assigning it minimum score so that it doesn't get selected over other valid tasks.
            score = MIN_INT;
        }
        return score + (reward * REWARD_FACTOR) - (expiresIn * EXPIRY_FACTOR);
    }

    private static Map<String, Integer> initializeMap() {
        Map<String,Integer> probabilityScoring = new HashMap<>();
        probabilityScoring.put("Piece of cake", 50000);
        probabilityScoring.put("Walk in the park", 50000);
        probabilityScoring.put("Quite likely", 10000);
        probabilityScoring.put("Risky", 7000);
        probabilityScoring.put("Gamble", 5000);
        probabilityScoring.put("Playing with fire", 1000);
        probabilityScoring.put("Rather detrimental", 1000);
        probabilityScoring.put("Suicide mission", 100);
        probabilityScoring.put("Hmmm....", 100);
        probabilityScoring.put("Sure thing", -10000);
        probabilityScoring.put("Impossible", -10000);
        return probabilityScoring;
    }


}
