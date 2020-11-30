package com.bigbank.dragonsofmugloar.model;

import java.util.Objects;

public class BuyItemResponse {
    private boolean shoppingSuccess;
    private int gold;
    private int lives;
    private int level;
    private int turn;

    public boolean isShoppingSuccess() {
        return shoppingSuccess;
    }

    public void setShoppingSuccess(boolean shoppingSuccess) {
        this.shoppingSuccess = shoppingSuccess;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyItemResponse that = (BuyItemResponse) o;
        return shoppingSuccess == that.shoppingSuccess &&
                gold == that.gold &&
                lives == that.lives &&
                level == that.level &&
                turn == that.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingSuccess, gold, lives, level, turn);
    }

    @Override
    public String toString() {
        return "BuyItemResponse{" +
                "shoppingSuccess=" + shoppingSuccess +
                ", gold=" + gold +
                ", lives=" + lives +
                ", level=" + level +
                ", turn=" + turn +
                '}';
    }
}
