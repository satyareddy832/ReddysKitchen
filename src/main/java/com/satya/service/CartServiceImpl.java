package com.satya.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.model.Cart;
import com.satya.model.CartItem;
import com.satya.model.Food;
import com.satya.model.User;
import com.satya.repo.CartItemRepo;
import com.satya.repo.CartRepo;
import com.satya.repo.FoodRepo;
import com.satya.repo.UserRepo;
import com.satya.request.AddCartItemRequest;


@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private CartRepo cart_repo;
	
	@Autowired
	private IUserService user_service;
	
	@Autowired
	private CartItemRepo cartItem_repo;
	
	@Autowired
	private IFoodService food_service;
	
	
	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user=user_service.findUserByJwtToken(jwt);
		Cart cart=cart_repo.findBycustomerId(user.getId());
		Food food=food_service.findFoodById(req.getFoodId());
		
		
		for(CartItem cartItem:cart.getItem()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity=cartItem.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		CartItem newCartItem=new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem savedCartItem=cartItem_repo.save(newCartItem); 
		cart.getItem().add(savedCartItem);
		cart_repo.save(cart);
		
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> opt=cartItem_repo.findById(cartItemId);
		if(opt.isEmpty()) {
			throw new Exception("Cannot find Cart Item");
		}
		CartItem cartItem=opt.get();
		cartItem.setQuantity(quantity); 
		cartItem.setTotalPrice(cartItem.getFood().getPrice()*quantity);
		
		return cartItem_repo.save(cartItem);
	}

	@Override
	public Cart removeItemFormCart(Long cartItemId, String jwt) throws Exception {
		User user=user_service.findUserByJwtToken(jwt);
		Cart cart=cart_repo.findBycustomerId(user.getId());
		
		Optional<CartItem> opt=cartItem_repo.findById(cartItemId);
		if(opt.isEmpty()) {
			throw new Exception("Cannot find Cart Item");
		}
		CartItem cartItem=opt.get();
		cartItem.setCart(null);
		cart.getItem().remove(cartItem);
		return cart_repo.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total=0L;
		for(CartItem cartItem:cart.getItem()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> opt=cart_repo.findById(id);
		if(opt.isEmpty()) {
			throw new Exception("Cannot find Cart with given id ");
		}
		return opt.get();
		
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		 Cart cart = cart_repo.findBycustomerId(userId);
		 cart.setTotal(calculateCartTotals(cart));
		 cart_repo.save(cart);
		 return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		Cart cart=findCartByUserId(userId);
		
		cart.getItem().clear();
		return cart_repo.save(cart);
	}

}
