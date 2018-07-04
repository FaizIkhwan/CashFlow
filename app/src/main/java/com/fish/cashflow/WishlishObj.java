package com.fish.cashflow;

public class WishlishObj
{
    private String description;
    private double percent;

    /**
     * Normal constructor.
     * @param description
     * @param percent
     */
    public WishlishObj(String description, double percent)
    {
        this.description = description;
        this.percent = percent;
    }

    /**
     * Getter.
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter.
     * @return percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * Setter.
     * @param percent
     */
    public void setPercent(double percent) {
        this.percent = percent;
    }
}
