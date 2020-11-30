package com.bigbank.dragonsOfMugloar.application.exception;

public class ShopItemNotFoundException extends RuntimeException {
    public ShopItemNotFoundException() {
        super("Shop Item not found");
    }
}
