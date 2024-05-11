package com.satya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Food;
import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.request.CreateFoodRequest;
import com.satya.response.MessageResponse;
import com.satya.service.IFoodService;
import com.satya.service.IRestaurantService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

	@Autowired
	private IFoodService food_service;
	
	@Autowired
	private IUserService user_service;
	
	@Autowired
	private IRestaurantService rest_service;
	
	
	@PostMapping()
	public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
										   @RequestHeader("Authorization") String jwt) throws Exception
	{
		Restaurant restaurant=rest_service.findRestaurantById(req.getRestaurantId());
		Food food=food_service.createFood(req, req.getCategory(), restaurant);
		
		return new ResponseEntity<Food>(food,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteFood(@PathVariable("id") Long id,
										   @RequestHeader("Authorization") String jwt) throws Exception
	{
		
		food_service.deleteFood(id);
		
		return new ResponseEntity<MessageResponse>(new MessageResponse("Food Deleted Successfully"),HttpStatus.OK);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Food> UpdateFoodAvailabilityStatus(@PathVariable("id") Long id,
										   @RequestHeader("Authorization") String jwt) throws Exception
	{
		
		Food food = food_service.updateAvailibilityStatus(id);
		
		return new ResponseEntity<Food>(food,HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
