package com.servermonks.assetmanagement.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.beans.BorrowHistory;

public interface BorrowHistoryService {
    boolean addBorrowHistory(BorrowHistory borrowHistory) throws SQLException;

    BorrowHistory getBorrowHistoryById(int borrowId) throws SQLException;

    List<BorrowHistory> getAllBorrowHistories() throws SQLException;

    boolean updateBorrowHistory(BorrowHistory borrowHistory) throws SQLException;

    boolean deleteBorrowHistory(int borrowId) throws SQLException;

	boolean isUserAlreadyHaveSameTypeAsset(int userid, int asset) throws SQLException;

	boolean isUserBannned(int userId) throws SQLException;

	List<BorrowHistory> getBorrowHistoryByUserId(int userId) throws SQLException;

	Date getNoOfDaysBanRemaining(int userId) throws SQLException;

	List<BorrowHistory> getBorrowHistoryByUserIdAndAssetId(int userId, int assetId) throws SQLException;

	boolean updateReturnDate(int userId, int assetId) throws SQLException;
	
	
}
