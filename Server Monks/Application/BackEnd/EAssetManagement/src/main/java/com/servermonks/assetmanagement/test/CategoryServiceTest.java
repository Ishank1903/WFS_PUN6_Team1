package com.servermonks.assetmanagement.test;


import java.sql.SQLException;
import java.util.List;

import com.servermonks.assetmanagement.beans.Category;
import com.servermonks.assetmanagement.exceptions.CategoryAlreadyPresentException;
import com.servermonks.assetmanagement.exceptions.CategoryNotFoundException;
import com.servermonks.assetmanagement.service.impl.CategoryServiceImpl;

public class CategoryServiceTest {

    public static void main(String[] args) {
        // Create an instance of the CategoryService
    	try {
    	CategoryServiceImpl categoryService = new CategoryServiceImpl();

        // Test the addCategory method
        Category newCategory = new Category();
        newCategory.setCategoryId(99);
        newCategory.setName("Test Category");
        newCategory.setLendingPeriod(7);
        newCategory.setLateFees(2.0);
        newCategory.setNoOfDaysBanned(3);

        boolean isCategoryAdded;
		
			isCategoryAdded = categoryService.addCategory(newCategory);
		
        if (isCategoryAdded) {
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add the category.");
        }

        // Test the getCategoryById method
        int categoryIdToRetrieve = 1;	// Replace with a valid category ID
        Category retrievedCategory;
		
			retrievedCategory = categoryService.getCategoryById(categoryIdToRetrieve);
		
        if (retrievedCategory != null) {
            System.out.println("Category ID: " + retrievedCategory.getCategoryId());
            System.out.println("Category Name: " + retrievedCategory.getName());
            // Print other category properties as needed
        } else {
            System.out.println("Category not found.");
        }

        // Test the getAllCategories method
        List<Category> allCategories = categoryService.getAllCategories();
        if (!allCategories.isEmpty()) {
            System.out.println("All Categories:");
            for (Category category : allCategories) {
                System.out.println("Category ID: " + category.getCategoryId());
                System.out.println("Category Name: " + category.getName());
                // Print other category properties as needed
            }
        } else {
            System.out.println("No categories found.");
        }

        // Test the updateCategory method
        int categoryIdToUpdate = 99; // Replace with a valid category ID
        Category categoryToUpdate = categoryService.getCategoryById(categoryIdToUpdate);
        if (categoryToUpdate != null) {
            categoryToUpdate.setName("Updated Category Name");
            boolean isCategoryUpdated = categoryService.updateCategory(categoryToUpdate);
            if (isCategoryUpdated) {
                System.out.println("Category updated successfully.");
            } else {
                System.out.println("Failed to update the category.");
            }
        } else {
            System.out.println("Category not found.");
        }

        // Test the deleteCategory method
        int categoryIdToDelete = 99; // Replace with a valid category ID
        boolean isCategoryDeleted = categoryService.deleteCategory(categoryIdToDelete);
        if (isCategoryDeleted) {
            System.out.println("Category deleted successfully.");
        } else {
            System.out.println("Failed to delete the category.");
        }
        
		}catch (SQLException | CategoryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoryAlreadyPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
