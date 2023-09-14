package com.servermonks.assetmanagement.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import com.servermonks.assetmanagement.beans.BorrowHistory;
import com.servermonks.assetmanagement.dao.BorrowHistoryDao;
import com.servermonks.assetmanagement.utils.DBUtil;

public class BorrowHistoryDaoImpl implements BorrowHistoryDao {
    private Connection connection;

    public BorrowHistoryDaoImpl() throws SQLException {
        connection = DBUtil.createConnection();
    }

    @Override
    public boolean addBorrowHistory(BorrowHistory borrowHistory) throws SQLException {
        String sql = "INSERT INTO BorrowHistory (borrowId, userId, assetId, borrowDate, returnDate, lateReturnFee, isBanned, bannedUntil) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowHistory.getBorrowId());
            preparedStatement.setInt(2, borrowHistory.getUserId());
            preparedStatement.setInt(3, borrowHistory.getAssetId());
            preparedStatement.setDate(4, new Date(borrowHistory.getBorrowDate().getTime()));
            preparedStatement.setDate(5, borrowHistory.getReturnDate() != null ?
                                          new Date(borrowHistory.getReturnDate().getTime()) : null);
            preparedStatement.setDouble(6, borrowHistory.getLateReturnFee());
            preparedStatement.setBoolean(7, borrowHistory.isBanned());
            preparedStatement.setDate(8, borrowHistory.getBannedUntil() != null ?
                                          new Date(borrowHistory.getBannedUntil().getTime()) : null);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
    }

    @Override
    public BorrowHistory getBorrowHistoryById(int borrowId) throws SQLException {
        String sql = "SELECT * FROM BorrowHistory WHERE borrowId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToBorrowHistory(resultSet);
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        return null;
    }

    @Override
    public List<BorrowHistory> getAllBorrowHistories() throws SQLException {
        List<BorrowHistory> borrowHistories = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                borrowHistories.add(mapResultSetToBorrowHistory(resultSet));
            }
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        return borrowHistories;
    }

    @Override
    public boolean updateBorrowHistory(BorrowHistory borrowHistory) throws SQLException {
        String sql = "UPDATE BorrowHistory SET userId = ?, assetId = ?, borrowDate = ?, " +
                     "returnDate = ?, lateReturnFee = ?, isBanned = ?, bannedUntil = ? WHERE borrowId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowHistory.getUserId());
            preparedStatement.setInt(2, borrowHistory.getAssetId());
            preparedStatement.setDate(3, new Date(borrowHistory.getBorrowDate().getTime()));
            preparedStatement.setDate(4, borrowHistory.getReturnDate() != null ?
                                          new Date(borrowHistory.getReturnDate().getTime()) : null);
            preparedStatement.setDouble(5, borrowHistory.getLateReturnFee());
            preparedStatement.setBoolean(6, borrowHistory.isBanned());
            preparedStatement.setDate(7, borrowHistory.getBannedUntil() != null ?
                                          new Date(borrowHistory.getBannedUntil().getTime()) : null);
            preparedStatement.setInt(8, borrowHistory.getBorrowId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
    }
    
    @Override
    public List<BorrowHistory> getUserCurrentBorrowHistory(int userId) throws SQLException {
        List<BorrowHistory> currentBorrowHistory = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory WHERE userId = ? AND returnDate IS NULL";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                currentBorrowHistory.add(mapResultSetToBorrowHistory(resultSet));
            }
        } catch (SQLException e) {
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        return currentBorrowHistory;
    }

    @Override
    public boolean deleteBorrowHistory(int borrowId) throws SQLException {
        String sql = "DELETE FROM BorrowHistory WHERE borrowId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, borrowId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
    }
    @Override
    public boolean updateLateReturnFeesAndBannedUntil() throws SQLException {
        // The above code refresh the dates   
    	try {
                // SQL query with JOIN and UPDATE
                String sql = "UPDATE BorrowHistory AS b " +
                             "JOIN Asset AS a ON b.assetId = a.assetId " +
                             "JOIN Category AS c ON a.typeId = c.categoryId " +
                             "SET b.lateReturnFee = c.lateFees, " +
                             "    b.bannedUntil = DATE_ADD(b.returnDate, INTERVAL c.noOfDaysBanned DAY) " +
                             "WHERE b.returnDate IS NOT NULL " +
                             "  AND DATEDIFF(b.returnDate, b.borrowDate) < c.lendingPeriod";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                int rowsUpdated = preparedStatement.executeUpdate();
                
                // Close the resources
                preparedStatement.close();
                
                return rowsUpdated > 0;
            }catch (SQLException e) {
            	//e.printStackTrace();
            	throw new SQLException("SQL Exception...  Please Contact Administrator");
            
            }
    	
       }
    @Override
    public boolean updateIsBannedStatus() throws SQLException {
        try {
        	// SQL query to update isBanned
            String sql = "UPDATE BorrowHistory SET isBanned = false WHERE CURDATE() >= bannedUntil";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            int rowsUpdated = preparedStatement.executeUpdate();
            
            // Close the resources
            preparedStatement.close();
            //connection.close();

            // Return true if any rows were updated, false otherwise
            return rowsUpdated > 0;
        } catch (SQLException e) {
        	//e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
    }
    
    @Override
    public boolean isUserBannedForCategory(int userId) throws SQLException {
        try {
        	
           // SQL query to check if a user is banned for a specific category
            String sql = "SELECT COUNT(*) AS count " +
                         "FROM BorrowHistory " +
                         "WHERE userId = ? " +
                         " AND isBanned = true";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
           

            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Check if the user is banned for the specified category
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
            
            // Close the resources
            resultSet.close();
            preparedStatement.close();
            //connection.close();
        } catch (SQLException e) {
        	e.printStackTrace();
        	throw new SQLException("SQL Exception...  Please Contact Administrator");
        
        }
        
        return false; // Default to false if there was an error or no matching records found.
    }

    @Override
    public List<BorrowHistory> getBorrowHistoryByUserId(int userId) throws SQLException {
        List<BorrowHistory> borrowHistories = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory WHERE userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrowHistories.add(mapResultSetToBorrowHistory(resultSet));
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return borrowHistories;
    }
    
    @Override
    public BorrowHistory getBorrowHistoryWithMaxBannedUntil(int userId) throws SQLException {
        String sql = "SELECT * FROM BorrowHistory WHERE userId = ? AND isBanned = true " +
                     "ORDER BY bannedUntil DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
        	return mapResultSetToBorrowHistory(resultSet);
        }
        return null;
    }
    
    @Override
    public List<BorrowHistory> getBorrowHistoryByUserIdAndAssetId(int userId, int assetId) throws SQLException {
        List<BorrowHistory> borrowHistories = new ArrayList<>();
        String sql = "SELECT * FROM BorrowHistory WHERE userId = ? AND assetId = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, assetId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                borrowHistories.add(mapResultSetToBorrowHistory(resultSet));
            }
        } catch (SQLException e) {
            throw new SQLException("SQL Exception...  Please Contact Administrator");
        }
        return borrowHistories;
    }
    
    @Override
    public boolean updateReturnDate(int userId, int assetId) throws SQLException {
        String sql = "UPDATE BorrowHistory SET returnDate = CURDATE() WHERE userId = ? AND assetId = ? AND returnDate IS NULL";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, assetId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("SQL Exception... Please Contact Administrator");
        }
    }
    
    private BorrowHistory mapResultSetToBorrowHistory(ResultSet resultSet) throws SQLException {
    
    	BorrowHistory borrowHistory = new BorrowHistory();
        borrowHistory.setBorrowId(resultSet.getInt("borrowId"));
        borrowHistory.setUserId(resultSet.getInt("userId"));
        borrowHistory.setAssetId(resultSet.getInt("assetId"));
        borrowHistory.setBorrowDate(resultSet.getDate("borrowDate"));
        borrowHistory.setReturnDate(resultSet.getDate("returnDate"));
        borrowHistory.setLateReturnFee(resultSet.getDouble("lateReturnFee"));
        borrowHistory.setBanned(resultSet.getBoolean("isBanned"));
        borrowHistory.setBannedUntil(resultSet.getDate("bannedUntil"));
        return borrowHistory;
        
    }
    
   
}
