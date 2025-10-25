package com.akps.exapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akps.exapp.model.Admin;
import com.akps.exapp.repository.AdminRepository;

@Service
public class AdminService 
{
	@Autowired
	private AdminRepository adminRepository;

	public Admin findByEmailAndPassword(String email, String password) 
	{
		// TODO Auto-generated method stub
		return adminRepository.findByEmailAndPassword(email, password);
	}

}
