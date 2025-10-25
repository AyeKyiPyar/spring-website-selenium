package com.akps.exapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akps.exapp.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
	
	Customer findByEmailAndPassword(String email, String password);

}
