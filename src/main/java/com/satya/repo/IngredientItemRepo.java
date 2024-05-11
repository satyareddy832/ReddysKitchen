package com.satya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.IngredientsItem;

public interface IngredientItemRepo extends JpaRepository<IngredientsItem, Long> {

	public List<IngredientsItem> findByRestaurantId(Long id);
	
	
}
