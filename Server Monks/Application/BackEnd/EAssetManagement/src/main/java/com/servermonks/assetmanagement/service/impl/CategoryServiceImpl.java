package com.servermonks.assetmanagement.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.Category;
import com.servermonks.assetmanagement.dao.CategoryDao;
import com.servermonks.assetmanagement.dao.impl.CategoryDaoImpl;
import com.servermonks.assetmanagement.exceptions.CategoryAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.service.CategoryService;
import com.servermonks.assetmanagement.storage.StorageFactory;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl() throws SQLException {
        // Initialize the CategoryDao (you can use dependency injection here)
        this.categoryDao = (CategoryDaoImpl) StorageFactory.getStorage(4);
    }

    @Override
    public boolean addCategory(Category category) throws SQLException, CategoryAlreadyPresentException {
        if(categoryDao.isCategoryPresentById(category.getCategoryId())){
        	
        	throw new CategoryAlreadyPresentException("The Category with ID is Already Present");
        
        }
    	return categoryDao.addCategory(category);
    
    }

    @Override
    public Category getCategoryById(int categoryId) throws SQLException, CategoryNotFoundException {
    	
    	Category temp=  categoryDao.getCategoryById(categoryId);
    	if(temp == null) {
    		throw new CategoryNotFoundException("Category Not Found Exception");
    	}
		return temp;
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        return categoryDao.getAllCategories();
    }

    @Override
    public boolean updateCategory(Category category) throws SQLException {
        return categoryDao.updateCategory(category);
    }

    @Override
    public boolean deleteCategory(int categoryId) throws SQLException {
        return categoryDao.deleteCategory(categoryId);
    }
}
