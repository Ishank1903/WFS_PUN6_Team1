package com.servermonks.assetmanagement.beans;

import java.util.Date;

public class BorrowHistory {
    private int borrowId;
    private int userId;
    private int assetId;
    private Date borrowDate;
    private Date returnDate;
    private double lateReturnFee;
    private boolean isBanned;
    private Date bannedUntil;

    
    public BorrowHistory() {
        // Default constructor
    }

    public BorrowHistory(int borrowId, int userId, int assetId, Date borrowDate, Date returnDate, double lateReturnFee, boolean isBanned, Date bannedUntil) {
        this.borrowId = borrowId;
        this.userId = userId;
        this.assetId = assetId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.lateReturnFee = lateReturnFee;
        this.isBanned = isBanned;
        this.bannedUntil = bannedUntil;
    }
    public BorrowHistory( int userId, int assetId, Date borrowDate, Date returnDate, double lateReturnFee, boolean isBanned, Date bannedUntil) {
        this.borrowId = 0;
        this.userId = userId;
        this.assetId = assetId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.lateReturnFee = lateReturnFee;
        this.isBanned = isBanned;
        this.bannedUntil = bannedUntil;
    }

    // Getter and Setter methods for all attributes

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public double getLateReturnFee() {
        return lateReturnFee;
    }

    public void setLateReturnFee(double lateReturnFee) {
        this.lateReturnFee = lateReturnFee;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Date getBannedUntil() {
        return bannedUntil;
    }

    public void setBannedUntil(Date bannedUntil) {
        this.bannedUntil = bannedUntil;
    }

    @Override
    public String toString() {
        return "BorrowHistory{" +
                "borrowId=" + borrowId +
                ", userId=" + userId +
                ", assetId=" + assetId +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", lateReturnFee=" + lateReturnFee +
                ", isBanned=" + isBanned +
                ", bannedUntil=" + bannedUntil +
                '}';
    }
}
