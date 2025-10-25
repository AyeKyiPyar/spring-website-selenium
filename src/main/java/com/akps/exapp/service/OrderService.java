package com.akps.exapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.Orders;
import com.akps.exapp.repository.OrderRepository;

@Service
public class OrderService 
{
	@Autowired
	private OrderRepository orderRepository;

	public Orders save(Orders order) 
	{
		return orderRepository.save(order);
		
	}

	public Orders findById(Integer id)
	{

		return orderRepository.getById(id);
	}

	public List<Orders> findAll() 
	{
		
		return orderRepository.findAll();
	}

}
