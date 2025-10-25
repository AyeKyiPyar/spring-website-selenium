package com.akps.exapp.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.Category;
import com.akps.exapp.model.Product;
import com.akps.exapp.repository.CategoryRepository;
import com.akps.exapp.repository.ProductRepository;

@Service
public class CategoryService 
{
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public Category save(Category category)
	{
		
		return categoryRepository.save(category);
	}

	public List<Category> findAll()
	{
		
		return categoryRepository.findAll();
	}

	public Category findById(Integer id) 
	{
		
		return  categoryRepository.findById(id).get();
	}

	public Category updateCategory(Category category) 
	{
		Category existingCategory = categoryRepository.findById(category.getId()).get();
		
		existingCategory.setName(category.getName());
		existingCategory.setDescription(category.getDescription());
		
		return categoryRepository.save(existingCategory);
	}

	public boolean deleteById(Integer id) 
	{
	
	 // Check if the product exists in the repository
        if (categoryRepository.existsById(id))
        {
        	// Delete all products associated with the category
    	  //  productRepository.deleteByCategoryId(id);
        	
        	productRepository.updateCategoryToNull(id);
        	
        	categoryRepository.deleteById(id); // Delete the product
        	
            return true;
        } 
        else 
        {
            return false; // Return false if the product doesn't exist
        }
	}

}
