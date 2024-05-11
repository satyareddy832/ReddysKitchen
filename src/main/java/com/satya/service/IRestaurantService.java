package com.satya.service;

import java.util.List;

import com.satya.dto.RestaurantDto;
import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.request.CreateRestaurantRequest;

public interface IRestaurantService {

	public Restaurant createRestaurant(CreateRestaurantRequest req,User user);
	
	public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updatedRestaurant) throws Exception;
	
	public void deleteRestaurant(Long restaurantId) throws Exception;
	
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyWord);
	
	
	public Restaurant findRestaurantById(Long id) throws Exception;
	
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	public RestaurantDto addToFavorites(Long restaurantId,User user) throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id) throws Exception;
	
	
	
	
	
	
	
}
