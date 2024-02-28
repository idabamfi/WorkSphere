package com.example.employeeapplication;

public class SalesTarget {

    private double salesTarget;

    private double salesAchieved;

    // Constructor without parameters
    public SalesTarget() {
    }

    // Constructor with parameters
    public SalesTarget(String dateGiven, double salesDifference, double salesTarget) {
        this.salesTarget = salesTarget;
        this.salesAchieved = salesAchieved;
    }

    public double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = salesTarget;
    }

    public double getSalesAchieved(double salesAchieved) {
        return salesAchieved;
    }

    public void setSalesAchieved(double salesAchieved) {
        this.salesAchieved = salesAchieved;
    }

}
