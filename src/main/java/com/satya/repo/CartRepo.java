 package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {
	
	public Cart findBycustomerId(Long id);
	
}
