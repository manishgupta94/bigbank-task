package com.bigbank.dragonsOfMugloar.application.exception;

/**
 * Exception class extending RuntimeException to be raised when Healing Shop Item
 * with name "Healing potion" is not found in API response of GET /api/v2/:gameId/messages.
 *
 * @author Manish Gupta
 * @version $Id: HealingPotionNotFoundException.java 1.0
 * @since 2020-11-28
 */
public class HealingPotionNotFoundException extends RuntimeException {
    public HealingPotionNotFoundException() {
        super("Healing Potion not found in Shop Items");
    }
}
