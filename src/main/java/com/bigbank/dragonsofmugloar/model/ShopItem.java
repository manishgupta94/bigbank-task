package com.bigbank.dragonsofmugloar.model;

import java.util.Objects;

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
