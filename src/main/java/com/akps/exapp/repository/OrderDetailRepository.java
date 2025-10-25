package com.akps.exapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akps.exapp.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>
{

}
