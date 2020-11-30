package com.bigbank.dragonsofmugloar.exception;

public class HealingPotionNotFoundException extends RuntimeException {
    public HealingPotionNotFoundException() {
        super("Healing Potion not found in Shop Items");
    }
}
