package com.servermonks.assetmanagement.dao;

import java.sql.SQLException;
import java.util.List;
import com.servermonks.assetmanagement.beans.Category;


public interface CategoryDao {
    boolean addCategory(Category category) throws SQLException;

    Category getCategoryById(int categoryId) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    boolean updateCategory(Category category)throws SQLException;

    boolean deleteCategory(int categoryId)throws SQLException;

	boolean isCategoryPresentById(int categoryId) throws SQLException;
}
