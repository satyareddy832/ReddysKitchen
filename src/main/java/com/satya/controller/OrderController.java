package com.satya.controller;

//WE HAVE TO SEE ORDER SERVICES and CONTROLLER VIDEO AGAIN to underStand it properly
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Order;
import com.satya.model.User;
import com.satya.request.CreateOrderRequest;
import com.satya.response.PaymentResponse;
import com.satya.service.IOrderService;
import com.satya.service.IPaymentService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private IOrderService order_service;
	
	@Autowired
	private IPaymentService payment_service;
	
	@Autowired
	private IUserService user_service;
	
	
	@PostMapping("/order")
	public ResponseEntity<PaymentResponse> createOrder(@RequestBody CreateOrderRequest req,
			                                 @RequestHeader("Authorization") String jwt ) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);
		Order order=order_service.createOrder(req, user);
		PaymentResponse res=payment_service.createPaymentLink(order);
		return new ResponseEntity<PaymentResponse>(res,HttpStatus.OK);
	}
	
	
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			                                 @RequestHeader("Authorization") String jwt ) throws Exception
	{
		User user=user_service.findUserByJwtToken(jwt);

		List<Order> usersOrders = order_service.getUsersOrder(user.getId());
		
		return new ResponseEntity<>(usersOrders,HttpStatus.OK);
	}
	
}
