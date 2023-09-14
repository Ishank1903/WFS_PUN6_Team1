package com.servermonks.assetmanagement.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import com.servermonks.assetmanagement.beans.BorrowHistory;

public interface BorrowHistoryDao {
    boolean addBorrowHistory(BorrowHistory borrowHistory) throws SQLException;

    BorrowHistory getBorrowHistoryById(int borrowId) throws SQLException;

    List<BorrowHistory> getAllBorrowHistories() throws SQLException;

    boolean updateBorrowHistory(BorrowHistory borrowHistory) throws SQLException;

    boolean deleteBorrowHistory(int borrowId) throws SQLException;

	List<BorrowHistory> getUserCurrentBorrowHistory(int userId) throws SQLException;

	boolean updateLateReturnFeesAndBannedUntil() throws SQLException;

	boolean updateIsBannedStatus() throws SQLException;

	boolean isUserBannedForCategory(int userId) throws SQLException;

	List<BorrowHistory> getBorrowHistoryByUserId(int userId) throws SQLException;

	BorrowHistory getBorrowHistoryWithMaxBannedUntil(int userId) throws SQLException;

	List<BorrowHistory> getBorrowHistoryByUserIdAndAssetId(int userId, int assetId) throws SQLException;

	boolean updateReturnDate(int userId, int assetId) throws SQLException;
    
}

