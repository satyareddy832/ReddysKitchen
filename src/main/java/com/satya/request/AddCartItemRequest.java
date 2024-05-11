package com.satya.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AddCartItemRequest {

	private Long foodId;
	private int quantity;
	
	private List<String> ingredients;
	
}
