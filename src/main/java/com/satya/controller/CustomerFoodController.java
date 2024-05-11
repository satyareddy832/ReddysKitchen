package com.satya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Food;
import com.satya.model.User;
import com.satya.response.MessageResponse;
import com.satya.service.IFoodService;
import com.satya.service.IRestaurantService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api/food")
public class CustomerFoodController {

	@Autowired
	private IFoodService food_service;
	
	@Autowired
	private IUserService user_service;
	
	@Autowired
	private IRestaurantService rest_service;
	
	@PostMapping("/search")
	public ResponseEntity<List<Food>> deleteFood(@RequestParam String keyword ,
										   @RequestHeader("Authorization") String jwt) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);
		
		List<Food> foods = food_service.searchFood(keyword);
		
		return new ResponseEntity<List<Food>>(foods,HttpStatus.OK);
	}
	
	@GetMapping("/restaurant/{rid}")
//	do not bother about the function name for this and the above method also
	public ResponseEntity<List<Food>> deleteFood(@PathVariable("rid") Long Id ,
												 @RequestParam(required=false) boolean veg,
												 @RequestParam(required=false) boolean seasonal,
												 @RequestParam(required=false) boolean nonveg,
												 @RequestParam(required=false) String food_category,
										   @RequestHeader("Authorization") String jwt) throws Exception
	{
		
		User user=user_service.findUserByJwtToken(jwt);
		
		List<Food> foods = food_service.getRestaurantFood(Id, veg, nonveg, seasonal, food_category);
		return new ResponseEntity<List<Food>>(foods,HttpStatus.OK);
	}
	
	
	
	
}
