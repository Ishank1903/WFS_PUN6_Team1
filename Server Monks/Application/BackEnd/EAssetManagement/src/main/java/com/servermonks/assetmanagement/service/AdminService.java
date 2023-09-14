package com.servermonks.assetmanagement.service;

import java.sql.SQLException;

public interface AdminService {

	boolean isAdminByUserId(int userId) throws SQLException;

}
