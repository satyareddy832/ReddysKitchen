package com.satya.request;

import com.satya.model.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class CreateOrderRequest {
	private Long restaurantId;
	private Address delivaryAddress;

}
