package com.bigbank.dragonsOfMugloar.application.model;

import java.util.Objects;

/**
 * DTO class mapping to capture the API response of GET /api/v2/:gameId/messages
 * which is mapped to List of this class. i.e. List<Ad>
 *
 * @author Manish Gupta
 * @version $Id: Ad.java 1.0
 * @since 2020-11-28
 */
public class Ad {
    private String adId;
    private String message;
    private int reward;
    private int expiresIn;
    private String probability;

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return reward == ad.reward &&
                expiresIn == ad.expiresIn &&
                Objects.equals(adId, ad.adId) &&
                Objects.equals(message, ad.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adId, message, reward, expiresIn);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "adId='" + adId + '\'' +
//                ", message='" + message + '\'' +
                ", reward=" + reward +
                ", expiresIn=" + expiresIn +
                ", probability='" + probability + '\'' +
                '}';
    }
}
