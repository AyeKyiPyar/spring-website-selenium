package com.akps.exapp;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.akps.exapp.controller.*;

@SpringBootApplication
public class SpringChpt01ExApplication
{

	public static void main(String[] args) 
	{
		//new File(AdminController.uploadDirectory).mkdir();
		new File(ProductController.uploadDirectory).mkdir();

		SpringApplication.run(SpringChpt01ExApplication.class, args);
	}

}
