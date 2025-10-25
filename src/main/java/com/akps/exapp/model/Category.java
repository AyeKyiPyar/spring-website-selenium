package com.akps.exapp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category 
{	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL) // Cascading delete 
	private List<Product> products;
	 
	
	public Category()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(Integer id, String name, String description) 
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public List<Product> getProducts() 
	{
		return products;
	}
	public void setProducts(List<Product> products) 
	{
		this.products = products;
	}
	
	

}
