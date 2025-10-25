package com.akps.exapp.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Product 
{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private Integer quantity;
	private Double price;
	private Date createdDate;
	private String image;
	
	@ManyToOne
	private Category category;
	
	public Product() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Product(Integer id, String name, String description, Integer quantity, Double price, Date createdDate,
			String image, Category category)
	{
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.createdDate = createdDate;
		this.image = image;
		this.category = category;
	}
	


	public String getImage()
	{
		return image;
	}



	public void setImage(String image) 
	{
		this.image = image;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getQuantity()
	{
		return quantity;
	}

	public void setQuantity(Integer quantity) 
	{
		this.quantity = quantity;
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

	public Category getCategory() 
	{
		return category;
	}

	public void setCategory(Category category) 
	{
		this.category = category;
	}
	
	
	
	
	
}
