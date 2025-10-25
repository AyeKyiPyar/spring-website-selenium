package com.akps.exapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.Product;
import com.akps.exapp.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	private ProductRepository productRepository;
	
	
	public Product save(Product product) 
	{
		
		return productRepository.save(product);
	}


	public List<Product> findAll() 
	{
		
		return productRepository.findAll();
	}


	public List<Product> findByCategoryId(Integer id) 
	{
		
		return productRepository.findByCategoryId(id);
	}


	public boolean deleteById(Integer id) 
	{
		
		 // Check if the product exists in the repository
        if (productRepository.existsById(id))
        {
            productRepository.deleteById(id); // Delete the product
            return true;
        } 
        else 
        {
            return false; // Return false if the product doesn't exist
        }
	}


	public Product findById(Integer id) 
	{
		
		return productRepository.getById(id);
	}


	public Product updateProduct(Product product)
	{
		Product existingProduct = productRepository.findById(product.getId()).get();
		
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setQuantity(product.getQuantity());
		existingProduct.setImage(product.getImage());
		existingProduct.setCreatedDate(product.getCreatedDate());
		existingProduct.setCategory(product.getCategory());
		
		return productRepository.save(existingProduct);
	}
	


}
