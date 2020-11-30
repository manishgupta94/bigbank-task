package com.bigbank.dragonsOfMugloar.application.model;

import java.util.Objects;

public class SolveMessageResponse {
    private boolean success;
    private int lives;
    private int gold;
    private int score;
    private int highScore;
    private int turn;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolveMessageResponse that = (SolveMessageResponse) o;
        return success == that.success &&
                lives == that.lives &&
                gold == that.gold &&
                score == that.score &&
                highScore == that.highScore &&
                turn == that.turn &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, lives, gold, score, highScore, turn, message);
    }

    @Override
    public String toString() {
        return "SolveMessageResponse{" +
                "success=" + success +
                ", lives=" + lives +
                ", gold=" + gold +
                ", score=" + score +
                ", highScore=" + highScore +
                ", turn=" + turn +
                ", message='" + message + '\'' +
                '}';
    }
}
