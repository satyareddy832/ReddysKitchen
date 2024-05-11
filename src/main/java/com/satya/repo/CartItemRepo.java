package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.CartItem;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

}
