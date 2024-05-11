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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.IngredientCategory;
import com.satya.model.IngredientsItem;
import com.satya.request.IngredientCategoryRequest;
import com.satya.request.IngredientItemRequest;
import com.satya.service.IIngredientService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
	
	@Autowired
	private IIngredientService ingredientService;

	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest req) throws Exception
	{
		
		IngredientCategory category=ingredientService.createIngredientCategory(req.getName(), req.getRestaurantId());
		
		return new ResponseEntity<IngredientCategory>(category,HttpStatus.CREATED);
	}
	
	
	@PostMapping("")
	public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientItemRequest req) throws Exception
	{
		
		IngredientsItem item = ingredientService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
		
		return new ResponseEntity<IngredientsItem>(item,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}/stoke")
	public ResponseEntity<IngredientsItem> updateIngredientItemStock(@PathVariable("id") Long id) throws Exception
	{
		
		IngredientsItem item = ingredientService.updateStock(id);
		
		return new ResponseEntity<IngredientsItem>(item,HttpStatus.OK);
	}
	
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<List<IngredientsItem>> getRestaurantIngredients(@PathVariable("id") Long id) throws Exception
	{
		
		List<IngredientsItem> item = ingredientService.findRestaurantIngredients(id);
		
		return new ResponseEntity<List<IngredientsItem>>(item,HttpStatus.OK);
	}
	
	
	@GetMapping("/restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCatgory(@PathVariable("id") Long id) throws Exception
	{
		
		List<IngredientCategory> categories = ingredientService.findIngredientCategorysByRestauantId(id);
		
		return new ResponseEntity<List<IngredientCategory>>(categories,HttpStatus.OK);
	}
	
	
	
}
