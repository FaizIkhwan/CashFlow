package com.fish.cashflow;

public class WishlishObj {
    private String description;
    private double percent;

    public WishlishObj(String description, double percent) {
        this.description = description;
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
