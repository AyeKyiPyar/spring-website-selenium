package com.akps.exapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akps.exapp.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>
{

}
