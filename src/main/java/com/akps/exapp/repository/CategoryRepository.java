package com.akps.exapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akps.exapp.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> 
{

}
