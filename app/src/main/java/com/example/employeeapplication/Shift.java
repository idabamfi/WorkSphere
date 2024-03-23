package com.example.employeeapplication;

public class Shift {
    private String shiftId;
    private String shiftDate;
    private String startTime;
    private String endTime;
    private String shiftType; // New field for shift type

    private String employeeId;
    private String employeeName;
    private double totalHours;
    private String lunchBreak;
    private SalesTarget salesTarget;
    private String clockInStatus;
    private String clockInTime;
    private String clockOutTime;

    // Default constructor (no-argument constructor) required by Firebase
    public Shift(String shiftId, String shiftDate, String shiftType, String lunchBreak) {
        // Required empty constructor
    }

    // Constructor with parameters
    public Shift(String shiftId, String shiftDate, String startTime, String endTime, String shiftType, String lunchBreak) {
        this.shiftId = shiftId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.lunchBreak = lunchBreak;
    }

    // Constructor with all fields
    public Shift(String shiftId, String shiftDate, String startTime, String endTime, String shiftType, String employeeId, String employeeName, double totalHours, String lunchBreak, SalesTarget salesTarget, String clockInStatus, String clockInTime, String clockOutTime) {
        this.shiftId = shiftId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shiftType = shiftType;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.totalHours = totalHours;
        this.lunchBreak = lunchBreak;
        this.salesTarget = salesTarget;
        this.clockInStatus = clockInStatus;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
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

    public String getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(String clockOutTime) {
        this.clockOutTime = clockOutTime;
    }
}