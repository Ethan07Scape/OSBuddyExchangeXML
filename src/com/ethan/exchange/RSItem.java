package com.ethan.exchange;

/**
 * Created by Ethan on 1/9/2018.
 */
public class RSItem {

    private String name;
    private long sellAverage;
    private long buyAverage;
    private long overallAverage;
    private long id;

    public RSItem(String name, long sellAverage, long buyAverage, long overallAverage, long id) {
        this.name = name;
        this.sellAverage = sellAverage;
        this.buyAverage = buyAverage;
        this.overallAverage = overallAverage;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getSellAverage() {
        return sellAverage;
    }

    public long getBuyAverage() {
        return buyAverage;
    }

    public long getOverallAverage() {
        return overallAverage;
    }

    public long getId() {
        return id;
    }
}