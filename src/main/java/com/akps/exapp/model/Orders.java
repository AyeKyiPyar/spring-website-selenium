package com.akps.exapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Orders 
{
	
	@Id  
	@GeneratedValue (strategy = GenerationType.IDENTITY) 
	private Integer id;
	private Date orderDate;
	private String status;
	private String billingAddress;
	private Double totalPrice;
	
	@ManyToOne
	private Customer customer;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	

	public Orders()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Orders(Integer id, Date orderDate, String status, String billingAddress, Double totalPrice,
			Customer customer) 
	{
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.status = status;
		this.billingAddress = billingAddress;
		this.totalPrice = totalPrice;
		this.customer = customer;
	}



	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Date getOrderDate() 
	{
		return orderDate;
	}

	public void setOrderDate(Date orderDate) 
	{
		this.orderDate = orderDate;
	}

	

	public String getStatus() 
	{
		return status;
	}
	


	public void setStatus(String status) 
	{
		this.status = status;
	}



	public String getBillingAddress() 
	{
		return billingAddress;
	}



	public void setBillingAddress(String billingAddress)
	{
		this.billingAddress = billingAddress;
	}



	public Double getTotalPrice() 
	{
		return totalPrice;
	}



	public void setTotalPrice(Double totalPrice) 
	{
		this.totalPrice = totalPrice;
	}



	public Customer getCustomer() 
	{
		return customer;
	}

	public void setCustomer(Customer customer) 
	{
		this.customer = customer;
	}



	public List<OrderDetail> getOrderDetails() 
	{
		return orderDetails;
	}



	public void setOrderDetails(List<OrderDetail> orderDetails)
	{
		this.orderDetails = orderDetails;
	}
	

}
