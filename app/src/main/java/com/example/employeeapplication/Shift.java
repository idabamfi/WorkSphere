package com.example.employeeapplication;

public class Shift {
    private String shiftId;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private String shiftType;
    private String employeeId;
    private String employeeName;
    private double totalHours;
    private double salesAchieved;
    private double salesTarget;
    private String lunchBreak;
    private String clockInStatus;
    private String clockInTime;
    private String clockOutTime;

    // No-argument constructor required by Firebase
    public Shift() {
        // Default constructor
    }
    public Shift(String shiftId, String shiftDate, String shiftType, String lunchBreak) {
        // Default constructor required for Firebase
        this.shiftId = shiftId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.lunchBreak = lunchBreak;
    }

    // Getters and setters

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

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

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public String getLunchBreak() {
        return lunchBreak;
    }

    public void setLunchBreak(String lunchBreak) {
        this.lunchBreak = lunchBreak;
    }

    public String getClockInStatus() {
        return clockInStatus;
    }

    public void setClockInStatus(String clockInStatus) {
        this.clockInStatus = clockInStatus;
    }
    public double getSalesAchieved() {
        return salesAchieved;
    }

    public void setSalesAchieved(double salesAchieved) {
        this.salesAchieved = salesAchieved;
    }

    public double getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(double salesTarget) {
        this.salesTarget = salesTarget;
    }
    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}