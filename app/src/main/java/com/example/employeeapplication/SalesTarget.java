package com.example.employeeapplication;

public class SalesTarget {
    private String dateGiven;
    private double salesDifference;
    private double salesTarget;

    // Constructor without parameters
    public SalesTarget() {
    }

    // Constructor with parameters
    public SalesTarget(String dateGiven, double salesDifference, double salesTarget) {
        this.dateGiven = dateGiven;
        this.salesDifference = salesDifference;
        this.salesTarget = salesTarget;
    }

    // Getters and setters
    public String getDateGiven() {
        return dateGiven;
    }

    public void setDateGiven(String dateGiven) {
        this.dateGiven = dateGiven;
    }

    public double getSalesDifference() {
        return salesDifference;
    }

    public void setSalesDifference(double salesDifference) {
        this.salesDifference = salesDifference;
    }

    public double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = salesTarget;
    }
}
