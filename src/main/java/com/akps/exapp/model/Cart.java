package com.akps.exapp.model;

import java.util.Date;

public class Cart 
{
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Date createdDate;
	private String image;
	
	private String categoryName;
	private Integer quantity;
	
	public Cart() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Cart(Integer id, String name, String description, Double price, Date createdDate, String image,
			String categoryName, Integer quantity)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.createdDate = createdDate;
		this.image = image;
		this.categoryName = categoryName;
		this.quantity = quantity;
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
	public Double getPrice() 
	{
		return price;
	}
	public void setPrice(Double price) 
	{
		this.price = price;
	}
	public Date getCreatedDate() 
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public String getCategoryName() 
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
{
		this.categoryName = categoryName;
	}
	public Integer getQuantity() 
	{
		return quantity;
	}
	public void setQuantity(Integer quantity)
	{
		this.quantity = quantity;
	}
	
	
}
