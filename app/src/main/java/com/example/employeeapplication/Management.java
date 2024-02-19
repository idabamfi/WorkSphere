package com.example.employeeapplication;

public class Management {
    private String managerId;
    private String managerName;
    private String managerNumber;

    // Constructor without parameters
    public Management() {
    }

    // Constructor with parameters
    public Management(String managerId, String managerName, String managerNumber) {
        this.managerId = managerId;
        this.managerName = managerName;
        this.managerNumber = managerNumber;
    }

    // Getters and setters
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerNumber() {
        return managerNumber;
    }

    public void setManagerNumber(String managerNumber) {
        this.managerNumber = managerNumber;
    }
}
