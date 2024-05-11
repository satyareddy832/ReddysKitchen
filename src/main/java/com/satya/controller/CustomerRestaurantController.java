package com.satya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satya.dto.RestaurantDto;
import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.request.CreateRestaurantRequest;
import com.satya.service.IRestaurantService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api/restaurants")
public class CustomerRestaurantController {

	@Autowired
	private IRestaurantService rest_service;
	
	@Autowired
	private IUserService user_service;
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		List<Restaurant> restaurants = rest_service.searchRestaurant(keyword);
		
		return new ResponseEntity<List<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurant(
			@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		List<Restaurant> restaurants = rest_service.getAllRestaurant();
		
		return new ResponseEntity<List<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		Restaurant restaurant = rest_service.findRestaurantById(id);
		
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
	}
	
	@PutMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDto> addToFavorites(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		RestaurantDto dto = rest_service.addToFavorites(id, user);
		
		return new ResponseEntity<RestaurantDto>(dto,HttpStatus.OK);
	}
	
	
	
}
