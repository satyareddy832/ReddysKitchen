package com.satya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satya.model.Cart;
import com.satya.model.CartItem;
import com.satya.request.AddCartItemRequest;
import com.satya.request.updateCartItemRequest;
import com.satya.service.ICartService;
import com.satya.service.IUserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IUserService user_service;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest req,
												  @RequestHeader("Authorization") String jwt ) throws Exception
	{
		CartItem cartItem=cartService.addItemToCart(req, jwt);
		
		return new ResponseEntity<CartItem>(cartItem,HttpStatus.OK);
	}
	
	
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updateCartItemQuantity(@RequestBody updateCartItemRequest req,
												  @RequestHeader("Authorization") String jwt ) throws Exception
	{
		CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(), req.getQuantity());
		
		return new ResponseEntity<CartItem>(cartItem,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(@PathVariable("id") Long id,
												  @RequestHeader("Authorization") String jwt ) throws Exception
	{
		Cart cart = cartService.removeItemFormCart(id, jwt);
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> updateCartItemQuantity(@RequestHeader("Authorization") String jwt ) throws Exception
	{
		Cart cart = cartService.clearCart(user_service.findUserByJwtToken(jwt).getId());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt ) throws Exception
	{
		Cart cart = cartService.findCartByUserId(user_service.findUserByJwtToken(jwt).getId());   
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@GetMapping("/carts/{cartId}/items")
	public ResponseEntity<List<CartItem>> findCartItemsByCartId(@PathVariable("cartId") Long cid,@RequestHeader("Authorization") String jwt) throws Exception{
		
		Cart cart= cartService.findCartById(cid);
		
		return new ResponseEntity<List<CartItem>>(cart.getItem(),HttpStatus.OK);
		
	}
	
	
	
}
