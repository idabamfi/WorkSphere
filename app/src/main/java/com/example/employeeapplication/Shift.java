package com.example.employeeapplication;

public class Shift {
    private String shiftId;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private double totalHours;
    private String lunchBreak;
    private SalesTarget salesTarget;
    private String clockInStatus;
    private String clockInTime;

    // Constructor without parameters
    public Shift() {
    }

    // Constructor with parameters
    public Shift(String shiftId, String shiftDate, String startTime, String endTime, double totalHours, String lunchBreak, SalesTarget salesTarget, String clockInStatus, String clockInTime) {
        this.shiftId = shiftId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
        this.lunchBreak = lunchBreak;
        this.salesTarget = salesTarget;
        this.clockInStatus = clockInStatus;
        this.clockInTime = clockInTime;
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

    public SalesTarget getSalesTarget() {
        return salesTarget;
    }

    public void setSalesTarget(SalesTarget salesTarget) {
        this.salesTarget = salesTarget;
    }

    public String getClockInStatus() {
        return clockInStatus;
    }

    public void setClockInStatus(String clockInStatus) {
        this.clockInStatus = clockInStatus;
    }

    public String getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(String clockInTime) {
        this.clockInTime = clockInTime;
    }
}
