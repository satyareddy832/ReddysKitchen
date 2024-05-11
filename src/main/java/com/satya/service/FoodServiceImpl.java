package com.satya.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.model.Category;
import com.satya.model.Food;
import com.satya.model.Restaurant;
import com.satya.repo.FoodRepo;
import com.satya.repo.RestaurantRepo;
import com.satya.request.CreateFoodRequest;


@Service
public class FoodServiceImpl implements IFoodService {

	@Autowired
	private FoodRepo food_repo;
	
	@Autowired
	private RestaurantRepo rest_repo;
	
	@Override
	public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
		
		Food food=new Food();
		food.setDescription(req.getDescription());
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasional());
		food.setVegetarian(req.isVegetarin());
		food.setCreationDate(new Date());
		
		Food savedFood=food_repo.save(food);
		
		restaurant.getFoods().add(savedFood);
		
		rest_repo.save(restaurant);
		
		return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		Food food=findFoodById(foodId);
		food.setRestaurant(null);
		food_repo.save(food);  
	}

	@Override
	public List<Food> getRestaurantFood(Long restaurantId, boolean isVeg, boolean isNonveg, boolean isSeasonal,
			String foodCategory) 
	{
		System.out.println("FoodServiceImpl.getRestaurantFood()aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		System.out.println("Food CAtegory is"+foodCategory);
		List<Food> foods=food_repo.findByRestaurantId(restaurantId);
		
		if(isVeg) {
			foods=filterByVeg(foods,isVeg);
		}
		if(isNonveg) {
			foods=filterByNonveg(foods,isNonveg);
		}
		if(isSeasonal) {
			foods=filterBySeasonal(foods,isSeasonal);
		}
		if(foodCategory!=null && !foodCategory.equals("")) {
			System.out.println("came in side Category bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
			foods=filerByCategory(foods,foodCategory);
		}
		
		return foods;
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		
		return foods.stream().filter(food->food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonveg(List<Food> foods, boolean isNonveg) {
		return foods.stream().filter(food->!food.isVegetarian()==isNonveg).collect(Collectors.toList());
	}

	private List<Food> filerByCategory(List<Food> foods, String foodCategory) {
		return foods.stream().filter(food->{
			if(food.getFoodCategory()!=null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			else {
				return false;
			}
		}).collect(Collectors.toList());
	}

	private List<Food> filterByVeg(List<Food> foods, boolean isVeg) {
		
		return foods.stream().filter(food->food.isVegetarian()==isVeg).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		
		return food_repo.searchFood(keyword);
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		Optional<Food> optional=food_repo.findById(foodId);
		if(optional.isEmpty()) {
			throw new Exception("Food is Not Found ");
		}
		return optional.get();
	}

	@Override
	public Food updateAvailibilityStatus(Long foodId) throws Exception {
		Food food=findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return food_repo.save(food);
	}

}
