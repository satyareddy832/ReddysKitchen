package com.satya.service;

import java.util.List;

import com.satya.model.Order;
import com.satya.model.User;
import com.satya.request.CreateOrderRequest;

public interface IOrderService {

	public Order createOrder(CreateOrderRequest req,User user) throws Exception;
	
	public Order updateOrder(Long orderId,String orderStatus) throws Exception;
	
	public void cancleOrder(Long orderId) throws Exception;
	
	public List<Order> getUsersOrder(Long userId) throws Exception;
	
	public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus);
	
	public Order findOrderById(Long oId) throws Exception;
	
	
}
