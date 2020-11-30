package com.bigbank.dragonsOfMugloar.application.exception;

/**
 * Exception class extending RuntimeException to be raised when API response
 * of GET /api/v2/:gameId/messages only contains "Healing Potion" and no other items
 * for Dragon Level Boost.
 *
 * @author Manish Gupta
 * @version $Id: HealingPotionNotFoundException.java 1.0
 * @since 2020-11-28
 */
public class ShopItemNotFoundException extends RuntimeException {
    public ShopItemNotFoundException() {
        super("Shop Item not found");
    }
}
