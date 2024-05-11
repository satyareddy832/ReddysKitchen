package com.satya.service;

import java.util.List;

import com.satya.model.Category;

public interface ICateoryService {

	public Category CreateCategory(String name,Long userId) throws Exception;
	
	public List<Category> findCategoryByRestaurantId(Long rid) throws Exception;
	
	public Category findCategoryById(Long cid) throws Exception;
	
	
}
