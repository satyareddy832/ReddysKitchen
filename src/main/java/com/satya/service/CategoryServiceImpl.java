package com.satya.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.model.Category;
import com.satya.model.Restaurant;
import com.satya.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements ICateoryService {
	
	@Autowired
	private IRestaurantService rest_service;
	
	@Autowired
	private CategoryRepo cat_repo;
	
	@Override
	public Category CreateCategory(String name, Long userId) throws Exception {

		
		
		Restaurant restaurant=rest_service.getRestaurantByUserId(userId);
		Category category=new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		
		return cat_repo.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long rest_id) throws Exception {
//		Restaurant restaurant=rest_service.getRestaurantByUserId(rest_id);
		return cat_repo.findByRestaurantId(rest_id);
	}

	@Override
	public Category findCategoryById(Long cid) throws Exception {
		Optional<Category> optional=cat_repo.findById(cid);
		if(optional.isEmpty()) {
			throw new Exception("Category is not Found");
		}
		
		return optional.get();
	}

}
