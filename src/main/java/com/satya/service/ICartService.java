package com.satya.service;

import com.satya.model.Cart;
import com.satya.model.CartItem;
import com.satya.model.User;
import com.satya.request.AddCartItemRequest;

public interface ICartService {
	
	public CartItem addItemToCart(AddCartItemRequest req,String jwt) throws Exception;
	
	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
	
	public Cart removeItemFormCart(Long cartItemId,String jwt)throws Exception;
	
	public Long calculateCartTotals(Cart cart) throws Exception;
	
	public Cart findCartById(Long id)throws Exception;
	
	public Cart findCartByUserId(Long userId)throws Exception;
	
	public Cart clearCart(Long userId)throws Exception;
	
}
