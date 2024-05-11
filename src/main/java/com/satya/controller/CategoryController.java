package com.satya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Category;
import com.satya.model.User;
import com.satya.service.ICateoryService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	private ICateoryService cat_service;

	@Autowired
	private IUserService user_service;
	
	
	
	@PostMapping("/admin/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
												   @RequestHeader("Authorization") String jwt ) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);
		
		Category createdCategory = cat_service.CreateCategory(category.getName(),user.getId());
		return new ResponseEntity<Category>(createdCategory,HttpStatus.CREATED);
	}
	
	
	
	@GetMapping("/category/restaurant/{id}")
	public ResponseEntity<List<Category>> getRestaurantCategory( 
			@PathVariable Long id,
												   @RequestHeader("Authorization") String jwt ) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);
		
		List<Category> categories = cat_service.findCategoryByRestaurantId(id);
		
		return new ResponseEntity<List<Category>>(categories,HttpStatus.CREATED);
	}
	
	
	
	
	
	
}
