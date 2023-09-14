package com.servermonks.assetmanagement.beans;

public class Admin {
    private int userId;

    public Admin() {
        // Default constructor
    }

    public Admin(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Admin [userId=" + userId + "]";
    }
}

