package com.example.employeeapplication;

public class Pay {
    private double hourlyPay;
    private double accumulatedPay;
    private String payDate;

    // Constructor without parameters
    public Pay() {
    }

    // Constructor with parameters
    public Pay(double hourlyPay, double accumulatedPay, String payDate) {
        this.hourlyPay = hourlyPay;
        this.accumulatedPay = accumulatedPay;
        this.payDate = payDate;
    }

    // Getters and setters
    public double getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(double hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    public double getAccumulatedPay() {
        return accumulatedPay;
    }

    public void setAccumulatedPay(double accumulatedPay) {
        this.accumulatedPay = accumulatedPay;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
