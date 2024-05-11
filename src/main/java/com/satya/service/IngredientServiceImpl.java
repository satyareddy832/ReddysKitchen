package com.satya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.model.IngredientCategory;
import com.satya.model.IngredientsItem;
import com.satya.model.Restaurant;
import com.satya.repo.IngredientCategoryRepo;
import com.satya.repo.IngredientItemRepo;


@Service
public class IngredientServiceImpl implements IIngredientService {
	
	@Autowired
	private IngredientCategoryRepo ingredientCategoryRepo;

	@Autowired
	private IngredientItemRepo  ingredientItemRepo;
	
	@Autowired
	private IRestaurantService rest_service;
	
	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant=rest_service.findRestaurantById(restaurantId);
		
		IngredientCategory ingredientCategory=new IngredientCategory();
		ingredientCategory.setName(name);
		ingredientCategory.setRestaurant(restaurant);
		return ingredientCategoryRepo.save(ingredientCategory);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		Optional<IngredientCategory> optional=ingredientCategoryRepo.findById(id);
		if(optional.isEmpty()) {
			throw new Exception("No ingredientCategory Found ");
		}
		
		return optional.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategorysByRestauantId(Long id) throws Exception {
		rest_service.findRestaurantById(id);
		return ingredientCategoryRepo.findByRestaurantId(id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientItemName, Long ingredientCategoryId)
			throws Exception 
	{	
		
		Restaurant restaurant=rest_service.findRestaurantById(restaurantId);
		IngredientCategory category=findIngredientCategoryById(ingredientCategoryId);
		
		IngredientsItem item=new IngredientsItem();
		item.setName(ingredientItemName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem savedItem=ingredientItemRepo.save(item);
		
		category.getIngredients().add(savedItem);
		ingredientCategoryRepo.save(category);
		
		return savedItem;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) throws Exception {
		
		return ingredientItemRepo.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long id) throws Exception {
		
		Optional<IngredientsItem> opt=ingredientItemRepo.findById(id);
		if(opt.isEmpty()) {
			throw new Exception("IngredientsItem Not Found");
		}
		IngredientsItem item=opt.get();
		
		item.setInStoke(!item.isInStoke());
		
		return ingredientItemRepo.save(item);
		
	}

}
