package com.fish.cashflow;

public class WishlishObj {
    private String description;
    private String price;
    private double percent;

    public WishlishObj(String description, String price, double percent) {
        this.description = description;
        this.price = price;
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
