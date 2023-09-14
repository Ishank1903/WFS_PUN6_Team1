package com.servermonks.assetmanagement.service;

import java.sql.SQLException;
import java.util.List;
import com.servermonks.assetmanagement.beans.Category;
import com.servermonks.assetmanagement.exceptions.CategoryAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;


public interface CategoryService {
	boolean addCategory(Category category) throws SQLException, CategoryAlreadyPresentException;

    Category getCategoryById(int categoryId) throws SQLException, CategoryNotFoundException;

    List<Category> getAllCategories() throws SQLException;

    boolean updateCategory(Category category) throws SQLException;

    boolean deleteCategory(int categoryId) throws SQLException;
    
}
