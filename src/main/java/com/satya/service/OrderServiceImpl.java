package com.satya.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.model.Address;
import com.satya.model.Cart;
import com.satya.model.CartItem;
import com.satya.model.Order;
import com.satya.model.OrderItem;
import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.repo.AddressRepo;
import com.satya.repo.OrderItemRepo;
import com.satya.repo.OrderRepo;
import com.satya.repo.RestaurantRepo;
import com.satya.repo.UserRepo;
import com.satya.request.CreateOrderRequest;

@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private OrderRepo order_repo;
	
	@Autowired
	private ICartService cart_service;
	
	@Autowired
	private OrderItemRepo orderItem_repo;
	
	@Autowired
	private AddressRepo address_repo;
	
	@Autowired
	private UserRepo user_repo;
	
	@Autowired
	private IRestaurantService rest_service;
	
	
	
	@Override
	public Order createOrder(CreateOrderRequest req, User user) throws Exception {
		Address address=req.getDelivaryAddress();
		Address savedAddress = address_repo.save(address);
		if(!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
			user_repo.save(user);
		}
		Restaurant restaurant = rest_service.findRestaurantById(req.getRestaurantId());
		
		Order createdOrder=new Order();
		createdOrder.setCustomer(user);
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderStatus("PENDING");
		
		
		Cart cart=cart_service.findCartByUserId(user.getId());
		
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItem cartItem:cart.getItem()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngredients());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem savedOrderItem = orderItem_repo.save(orderItem);
			orderItems.add(savedOrderItem);
		}
		
		Long tot=cart_service.calculateCartTotals(cart);
		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrice(tot);
		createdOrder.setTotalAmount(tot);
		Order savedOrder = order_repo.save(createdOrder);
		restaurant.getOrders().add(savedOrder);
		
		
		return savedOrder;
	}
//WE HAVE TO SEE ORDER SERVICES VIDEO AGAIN to underStand it properly
	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		Order order=findOrderById(orderId);
		if( orderStatus.equals("OUT_FOR_DELIVERY")
		 || orderStatus.equals("DELIVERED")
		 || orderStatus.equals("COMPLETED")
		 || orderStatus.equals("PENDING")
		 ) {
			order.setOrderStatus(orderStatus);
			return order_repo.save(order);
		}
		throw new Exception("Please Select a valid Order Status");
	}

	@Override
	public void cancleOrder(Long orderId) throws Exception {
		order_repo.deleteById(orderId);
		
	}

	@Override
	public List<Order> getUsersOrder(Long userId) throws Exception {
		return order_repo.findByCustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) {
		
		List<Order> orders=order_repo.findByRestaurantId(restaurantId);
		
		if(orderStatus!=null) {
			orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long oId) throws Exception {
		Optional<Order> opt=order_repo.findById(oId);
		if(opt.isEmpty()) {
			throw new Exception("Order Not Found With Given Id ");
		}
		
		return opt.get();
	}

	
	
	
}
