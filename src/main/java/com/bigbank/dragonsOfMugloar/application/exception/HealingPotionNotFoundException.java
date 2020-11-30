package com.bigbank.dragonsOfMugloar.application.exception;

public class HealingPotionNotFoundException extends RuntimeException {
    public HealingPotionNotFoundException() {
        super("Healing Potion not found in Shop Items");
    }
}
