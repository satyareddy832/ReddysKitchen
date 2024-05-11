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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Order;
import com.satya.model.User;
import com.satya.request.CreateOrderRequest;
import com.satya.service.IOrderService;
import com.satya.service.IUserService;


@RestController
@RequestMapping("/api/admin")

public class AdminOrderController {

	@Autowired
	private IOrderService order_service;
	
	@Autowired
	private IUserService user_service;
	
	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(@PathVariable("id") Long id, 
													   @RequestParam(required = false) String orderStatus,	
													   @RequestHeader("Authorization") String jwt ) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);
		
		List<Order> restaurantOrders = order_service.getRestaurantOrder(id, orderStatus);
		
		return new ResponseEntity<>(restaurantOrders,HttpStatus.OK);
	}
	
	
	@PutMapping("/order/{orderId}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, 
													   @PathVariable String orderStatus,	
													   @RequestHeader("Authorization") String jwt ) throws Exception
	{	
		Order updateOrder = order_service.updateOrder(orderId, orderStatus);
		
		return new ResponseEntity<>(updateOrder,HttpStatus.OK);
	}
	
	
}
