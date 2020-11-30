package com.bigbank.dragonsOfMugloar.application.model;

import java.util.Objects;

/**
 * DTO class mapping to capture the API response of GET /api/v2/:gameId/shop
 * i.e when user get list of shop items, the List of this DTO class (List<ShopItem>)
 * stores the response object.
 *
 * @author Manish Gupta
 * @version $Id: ShopItem.java 1.0
 * @since 2020-11-28
 */
public class ShopItem {
    private String id;
    private String name;
    private int cost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopItem shopItem = (ShopItem) o;
        return cost == shopItem.cost &&
                Objects.equals(id, shopItem.id) &&
                Objects.equals(name, shopItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost);
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
