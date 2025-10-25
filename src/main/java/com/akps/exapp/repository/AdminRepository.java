package com.akps.exapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akps.exapp.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>
{

	Admin findByEmailAndPassword(String email, String password);



	

}
