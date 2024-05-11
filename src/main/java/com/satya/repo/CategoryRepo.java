package com.satya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	public List<Category> findByRestaurantId(Long rid);
	
	
}
