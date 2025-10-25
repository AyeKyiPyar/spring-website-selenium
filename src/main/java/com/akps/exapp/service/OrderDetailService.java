package com.akps.exapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.OrderDetail;
import com.akps.exapp.repository.OrderDetailRepository;
import com.akps.exapp.repository.OrderRepository;

@Service
public class OrderDetailService 
{
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	public OrderDetail save(OrderDetail detail) 
	{
		return orderDetailRepository.save(detail);
		
	}
	

}
