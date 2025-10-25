package com.akps.exapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderDetail 
{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Orders order;
	

	@ManyToOne
	private Product product;
	
	private Integer quantity;
	private Double subPrice;
	
	public OrderDetail() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDetail(Orders order, Product product, Integer quantity, Double subPrice) 
	{
		super();
		this.order = order;
		this.product = product;
		this.quantity = quantity;
		this.subPrice = subPrice;
	}

	public Orders getOrder() 
	{
		return order;
	}

	public void setOrder(Orders order) 
	{
		this.order = order;
	}

	public Product getProduct() 
	{
		return product;
	}

	public void setProduct(Product product) 
	{
		this.product = product;
	}

	public Integer getQuantity() 
	{
		return quantity;
	}

	public void setQuantity(Integer quantity) 
	{
		this.quantity = quantity;
	}

	public Double getSubPrice() 
	{
		return subPrice;
	}

	public void setSubPrice(Double subPrice) 
	{
		this.subPrice = subPrice;
	}
	
	
}
