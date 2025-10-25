package com.akps.exapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.Customer;
import com.akps.exapp.repository.CustomerRepository;

@Service
public class CustomerService 
{
	@Autowired
	private CustomerRepository customerRepository;
	
	
	public Customer findByEmailAndPassword(String email, String password) 
	{
		return customerRepository.findByEmailAndPassword(email, password);
		
	}


	public Customer save(Customer customer)
	{
		
		return customerRepository.save(customer);
	}
	

}
