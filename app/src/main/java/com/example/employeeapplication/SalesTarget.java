package com.example.employeeapplication;

public class SalesTarget {
    private double salesTarget;
    private double salesAchieved;

    public SalesTarget() {
        // Required no-argument constructor
    }

    public SalesTarget(double salesTarget, double salesAchieved) {
        this.salesTarget = salesTarget;
        this.salesAchieved = salesAchieved;
    }

    public double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = salesTarget;
    }

    public double getSalesAchieved() {
        return salesAchieved;
    }

    public void setSalesAchieved(double salesAchieved) {
        this.salesAchieved = salesAchieved;
    }
}