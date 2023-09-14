package com.servermonks.assetmanagement.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.servermonks.assetmanagement.beans.Asset;
import com.servermonks.assetmanagement.beans.BorrowHistory;
import com.servermonks.assetmanagement.dao.BorrowHistoryDao;
import com.servermonks.assetmanagement.dao.impl.BorrowHistoryDaoImpl;
import com.servermonks.assetmanagement.service.BorrowHistoryService;
import com.servermonks.assetmanagement.storage.StorageFactory;

public class BorrowHistoryServiceImpl implements BorrowHistoryService {
    private BorrowHistoryDao borrowHistoryDao;

    public BorrowHistoryServiceImpl() throws SQLException {
        //borrowHistoryDao = new BorrowHistoryDaoImpl();
        borrowHistoryDao = (BorrowHistoryDaoImpl) StorageFactory.getStorage(3);
    }

    @Override
    public boolean addBorrowHistory(BorrowHistory borrowHistory) throws SQLException {
        return borrowHistoryDao.addBorrowHistory(borrowHistory);
    }

    @Override
    public BorrowHistory getBorrowHistoryById(int borrowId) throws SQLException {
        return borrowHistoryDao.getBorrowHistoryById(borrowId);
    }

    @Override
    public List<BorrowHistory> getAllBorrowHistories() throws SQLException {
        return borrowHistoryDao.getAllBorrowHistories();
    }

    @Override
    public boolean updateBorrowHistory(BorrowHistory borrowHistory) throws SQLException {
        return borrowHistoryDao.updateBorrowHistory(borrowHistory);
    }

    @Override
    public boolean deleteBorrowHistory(int borrowId) throws SQLException {
        return borrowHistoryDao.deleteBorrowHistory(borrowId);
    }

	@Override
	public boolean isUserAlreadyHaveSameTypeAsset(int userid,int asset) throws SQLException {
		List<BorrowHistory> blist = borrowHistoryDao.getUserCurrentBorrowHistory(userid);
		for(BorrowHistory b : blist) {
			if(b.getAssetId() == asset) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean isUserBannned(int userId) throws SQLException {
		
		borrowHistoryDao.updateLateReturnFeesAndBannedUntil();
		borrowHistoryDao.updateIsBannedStatus();
		
		// Update Table Records for user then check for the things.
		
		return borrowHistoryDao.isUserBannedForCategory(userId);
	
	}
	@Override
	public List<BorrowHistory> getBorrowHistoryByUserId(int userId) throws SQLException {
		
			return borrowHistoryDao.getBorrowHistoryByUserId(userId);
		
	}

	@Override
	public Date getNoOfDaysBanRemaining(int userId) throws SQLException {
		
		BorrowHistory b = borrowHistoryDao.getBorrowHistoryWithMaxBannedUntil(userId);
		
		return b.getBannedUntil();
		
	}
	
	@Override
	public List<BorrowHistory> getBorrowHistoryByUserIdAndAssetId(int userId, int assetId) throws SQLException {
	    
		return borrowHistoryDao.getBorrowHistoryByUserIdAndAssetId(userId, assetId);
	    
	}

	@Override
	public boolean updateReturnDate(int userId, int assetId) throws SQLException {
	    
		List<BorrowHistory> borrowHistories = getBorrowHistoryByUserIdAndAssetId(userId, assetId);
	    	return borrowHistoryDao.updateReturnDate(userId, assetId);
	    
	}

	
}

