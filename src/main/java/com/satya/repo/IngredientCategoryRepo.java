package com.satya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.IngredientCategory;

public interface IngredientCategoryRepo extends JpaRepository<IngredientCategory, Long> {
	
	public List<IngredientCategory> findByRestaurantId(Long rid);

}
