package com.servermonks.assetmanagement.dao;

import java.sql.SQLException;

public interface AdminDao {

	boolean isAdminByUserId(int userId) throws SQLException;

}
