package com.satya.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class IngredientItemRequest {
	
	private String name;
	
	private Long categoryId;
	
	private Long restaurantId;
	

}
