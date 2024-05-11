package com.satya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.satya.model.Food;

public interface FoodRepo extends JpaRepository<Food, Long> {
	List<Food> findByRestaurantId(Long restaurantId);
	
	
	@Query("SELECT f from Food f where lower(f.name) LIKE lower(concat('%',:keyword,'%')) "
			+ "OR lower(f.foodCategory.name	) LIKE lower(concat('%',:keyword,'%'))")
	List<Food> searchFood(@Param("keyword") String keyword);

}
