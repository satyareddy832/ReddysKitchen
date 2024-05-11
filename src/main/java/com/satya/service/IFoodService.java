  package com.satya.service;

import java.util.List;

import com.satya.model.Category;
import com.satya.model.Food;
import com.satya.model.Restaurant;
import com.satya.request.CreateFoodRequest;

public interface IFoodService 
{
	public Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant);
	
	public void deleteFood(Long foodId) throws Exception;
	
	public List<Food> getRestaurantFood(Long restaurantId,boolean isVeg,boolean isNonveg,boolean isSeasonal,String foodCategory);
	
	public List<Food> searchFood(String keyword);
	
	public Food findFoodById(Long foodId) throws Exception;
	
	public Food updateAvailibilityStatus(Long foodId)throws Exception;
	
	
	

}
