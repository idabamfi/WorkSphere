package com.example.employeeapplication;
import java.util.List;
import java.util.Map;

public class Employee {
    private String employeeId;
    private String employeeName;
    private String employeeNumber;
    private int holidayDays;

    private double hourlyPay;
    private Pay pay;
    private Map<String, Shift> shifts;
    private List<NotificationMessage> notifications;

    // Constructor without parameters
    public Employee() {
    }

    // Constructor with parameters
    public Employee(String employeeId, String employeeName, String employeeNumber, double hourlyPay, int holidayDays, Pay pay, Map<String, Shift> shifts, List<NotificationMessage> notifications) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeNumber = employeeNumber;
        this.holidayDays = holidayDays;
        this.pay = pay;
        this.shifts = shifts;
        this.hourlyPay = hourlyPay;
        this.notifications = notifications;
    }

    // Getters and setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(int holidayDays) {
        this.holidayDays = holidayDays;
    }

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    public Map<String, Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Map<String, Shift> shifts) {
        this.shifts = shifts;
    }

    public List<NotificationMessage> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationMessage> notifications) {
        this.notifications = notifications;
    }

    // Getter methods
    public String getName() {
        return employeeName;
    }

    public String getNumber() {
        return employeeNumber;
    }

    public double getHourlyPay() {
        return hourlyPay;
    }

    public String getId() {
        return employeeId;
    }
}
