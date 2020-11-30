package com.bigbank.dragonsOfMugloar.application.util;

import com.bigbank.dragonsOfMugloar.application.exception.HealingPotionNotFoundException;
import com.bigbank.dragonsOfMugloar.application.exception.ShopItemNotFoundException;
import com.bigbank.dragonsOfMugloar.application.model.Ad;
import com.bigbank.dragonsOfMugloar.application.model.Game;
import com.bigbank.dragonsOfMugloar.application.model.ShopItem;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This is a class providing utility methods which is used by GameEngine. This class contains algorithm
 * to select the best Ad/Message to attempt based on probability score, rewards, and expiry time.
 * This also decides whether to buy ShopItem or not based on Lives and Gold value of the game.
 *
 *
 *
 * @author Manish Gupta
 * @version $Id: GameHelper.java 1.0
 * @since 2020-11-28
 */
@Component
public class GameHelper {

    /** If a task with the same probability fails 3 times, decrease its probability score by this constant */
    private static final int PROBABILITY_DECREASE_FACTOR = 30000;

    /** This map contains all the probability and its corresponding score. this score
     * is used to calculate the best task to be solved.*/
    private static Map<String, Integer> probabilityScore = initializeMap();

    /** Map used to store probability as Key, and how many times task with this probability failed */
    private static Map<String, Integer> failureNumberForEachProbability = new HashMap<>();

    private static final int REWARD_FACTOR = 100;
    private static final int EXPIRY_FACTOR = 250;


    /**
     * A method that takes in list of Ads, creates a map that contains score for each ads, and then
     * returns the ad with maximum score. ad with maximum score signifies that attempting this Ad has highest
     * chances of success with better reward.
     *
     * @param adList list of ads that can be attempted.
     * @return an Ad object which should be attempted out of all the Ads for maximum profit.
     */
    public Ad selectBestMessageToSolve(List<Ad> adList) {
        Map<Ad, Integer> adScore = new HashMap<>();

        //call calculateScore to calculate score of each Ad and append the Map(Ad, score) to adScore Map
        adList.forEach(ad ->
                adScore.put(ad, calculateScore(ad.getExpiresIn(), ad.getReward(), ad.getProbability()
                )));

        //Finding the Ad with the maximum score by traversing through adScore Map.
        return Collections.max(adScore.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();

    }

    /**
     * This method is called by GameEngine when a task fails. the method takes input the
     * probability of the task that failed, and updates the failureNumberForEachProbability Map.
     * The Map failureNumberForEachProbability stores probability as Key, and number of times it
     * failed as value.
     * Also, if tasks with certain type of probability has failed 3 times, it decreases the
     * probability score for that probability inside probabilityScore Map.
     *
     * @param probability of the probability of last task attempted which failed.
     */
    public void updateProbabilityScore(String probability) {
        //Insert failure rate for the probability for which task attempt failed.
        if(failureNumberForEachProbability.containsKey(probability)) {
            failureNumberForEachProbability.put(probability, failureNumberForEachProbability.get(probability) + 1);
        } else {
            failureNumberForEachProbability.put(probability, 1);
        }
        //Decrease the Probability Score by 50,000 if there are 3 or more failure rates for that probability.
        if(failureNumberForEachProbability.get(probability) >= 3) {
            probabilityScore.put(probability, probabilityScore.get(probability) - PROBABILITY_DECREASE_FACTOR);
        }
    }

    /**
     * A method that takes in list of shop items, and returns an Item that will heal lives
     *
     * @param shopItems list of shop items available.
     * @return an Item object for Healing Lives.
     */
    public ShopItem getHealingItemToBuy(List<ShopItem> shopItems) {
        return shopItems.stream()
                .filter(shopItem -> shopItem.getName().contains("Healing potion"))
                .findAny().orElseThrow(HealingPotionNotFoundException::new);
    }

    /**
     * A method that takes in last task success boolean value, all the items in the shop, and
     * the current game object and decide whether to purchase dragon level increasing shop items,
     * or not.
     *
     * @param lastTaskSuccess whether last task was successful or not
     * @param shopItems list of shop items available.
     * @param game the game object.
     * @return an optional shopitem which might or might not have dragon level increasing shop item.
     */
    public Optional<ShopItem> dragonLevelBoostItemToBuy(boolean lastTaskSuccess, List<ShopItem> shopItems, Game game) {
        ShopItem healingShopItem = getHealingItemToBuy(shopItems);

        //List to store IDs Dragon Level Booster Item
        Optional<ShopItem> dragonLevelBoostItemToBuy = Optional.empty();

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
            dragonLevelBoostItemToBuy = Optional.ofNullable(dragonLevelBoostItem);
        }

        //In case last task was failure, buy cheapest Items if current gold value is
        //100 more than cheapest item saving 100 for health potion
        if(!lastTaskSuccess && game.getGold() > uniqueCostOfDragonItems.get(0) + (2 * healingShopItem.getCost())) {
            ShopItem dragonLevelBoostItem = shopItems.stream()
                    .filter(shopItem -> shopItem.getCost() == uniqueCostOfDragonItems.get(0))
                    .findAny().orElseThrow(ShopItemNotFoundException::new);
            dragonLevelBoostItemToBuy = Optional.ofNullable(dragonLevelBoostItem);
        }
        return dragonLevelBoostItemToBuy;
    }

    /**
     * A method that takes in last task success boolean value, all the items in the shop, and
     * the current game object and decide whether to purchase dragon level increasing shop items,
     * or not.
     *
     * @param emptyMap an empty map to clear the failureNumberForEachProbability
     *                                       for new Game run.
     */
    public static void setFailureNumberForEachProbability(Map<String, Integer> emptyMap) {
        GameHelper.failureNumberForEachProbability = emptyMap;
    }

    public static void initializeProbabilityScore() {
        GameHelper.probabilityScore = initializeMap();
    }

    /**
     * A method that takes in message's expiresIn, reward and difficulty level, and assigns
     * a score to the message.
     * Note if the probability of the message is not in the probabilityScoring Map, the
     * message is corrupted, and the message with that id doesn't exist in the system.
     * We assign the minimum integer value to such corrupt messages so that it never gets
     * selected.
     *
     * @param expiresIn the number of turns after which message will expire.
     * @param reward reward achieved for solving the message.
     * @param probability the difficulty level of the message.
     * @return a score signifying the attempt desirability of the message.
     */
    private int calculateScore(int expiresIn, int reward, String probability) {
        int score;
        if(probabilityScore.containsKey(probability)) {
            score = probabilityScore.get(probability);
        } else {
            //In case, probability is not in the PROBABILITY_SCORE Map, this means task is with corrupt probability
            // name, and non-existent Id. Assigning it minimum score so that it doesn't get selected over other valid tasks.
            return Integer.MIN_VALUE;
        }
        return score + (reward * REWARD_FACTOR) - (expiresIn * EXPIRY_FACTOR);
    }

    /**
     * Call this method to initialize the probabilityScore Map for every new game.
     * This is a base score assigned to different probability. The map gets updated
     * dynamically as the game progresses.
     *
     * @return a probability score Map with base scores based on difficulty level
     */
    private static Map<String, Integer> initializeMap() {
        Map<String,Integer> probabilityScoring = new HashMap<>();
        probabilityScoring.put("Piece of cake", 50000);
        probabilityScoring.put("Walk in the park", 50000);
        probabilityScoring.put("Sure thing", 30000);
        probabilityScoring.put("Quite likely", 10000);
        probabilityScoring.put("Risky", 7000);
        probabilityScoring.put("Gamble", 5000);
        probabilityScoring.put("Playing with fire", 1000);
        probabilityScoring.put("Rather detrimental", 1000);
        probabilityScoring.put("Suicide mission", 100);
        probabilityScoring.put("Hmmm....", 100);
        probabilityScoring.put("Impossible", -100000);
        return probabilityScoring;
    }


}
