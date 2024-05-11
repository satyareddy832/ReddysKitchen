package com.satya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.request.CreateRestaurantRequest;
import com.satya.response.MessageResponse;
import com.satya.service.IRestaurantService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
	
	@Autowired
	private IRestaurantService rest_service;
	
	@Autowired
	private IUserService user_service;
	
	
	@PostMapping
	public ResponseEntity<Restaurant> createRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		System.out.println("hii");
		Restaurant restaurant = rest_service.createRestaurant(req, user);
		
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Restaurant> updateRestaurant(
			@RequestBody CreateRestaurantRequest req,
			@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		Restaurant restaurant = rest_service.updateRestaurant(id, req);
		
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteRestaurant(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		
		rest_service.deleteRestaurant(id);
		MessageResponse res=new MessageResponse();
		res.setMessage("Restaurant Deleted Successfully ");
		return new ResponseEntity<MessageResponse>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<Restaurant> updateRestaurantStatus(
			@RequestHeader("Authorization") String jwt,
			@PathVariable("id") Long id) throws Exception
	{
		
		Restaurant restaurant = rest_service.updateRestaurantStatus(id);
		
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
	}
	
	
	@GetMapping("/user")
	public ResponseEntity<Restaurant> findRestaurantByUserId(
			@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user= user_service.findUserByJwtToken(jwt);
		Restaurant restaurant = rest_service.getRestaurantByUserId(user.getId());
		
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
	}

	
	
}
