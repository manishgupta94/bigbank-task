package com.bigbank.dragonsofmugloar.exception;

public class ShopItemNotFoundException extends RuntimeException {
    public ShopItemNotFoundException() {
        super("Shop Item not found");
    }
}
