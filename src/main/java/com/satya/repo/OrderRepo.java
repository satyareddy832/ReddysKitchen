package com.satya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

	public List<Order> findByCustomerId(Long id);
	
	public List<Order> findByRestaurantId(Long id);
	
}
