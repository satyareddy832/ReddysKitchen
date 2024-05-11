package com.satya.service;

import java.util.List;

import com.satya.model.IngredientCategory;
import com.satya.model.IngredientsItem;


// INSIDE IngredientsERVICE ONLY WE ARE GOING TO HANDLE BOTH IngredientsERVICE AND IngredientCATEGORY ALSO
public interface IIngredientService {
	
	public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;
	
	public IngredientCategory findIngredientCategoryById(Long id)throws Exception;
	
	public List<IngredientCategory> findIngredientCategorysByRestauantId(Long id) throws Exception;
	
	public IngredientsItem createIngredientItem(Long restaurantId,String ingredientItemName,Long ingredientCategoryId) throws Exception;
	
	public List<IngredientsItem> findRestaurantIngredients(Long restaurentId) throws Exception;
	
	public IngredientsItem updateStock(Long id) throws Exception; 

}
