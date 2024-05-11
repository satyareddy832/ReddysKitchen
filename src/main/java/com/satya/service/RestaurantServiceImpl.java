package com.satya.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.dto.RestaurantDto;
import com.satya.model.Address;
import com.satya.model.Restaurant;
import com.satya.model.User;
import com.satya.repo.AddressRepo;
import com.satya.repo.RestaurantRepo;
import com.satya.repo.UserRepo;
import com.satya.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImpl implements IRestaurantService {

	@Autowired
	RestaurantRepo rest_repo;
	
	@Autowired
	private AddressRepo address_repo;
	
	
	@Autowired
	private UserRepo user_repo;
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		Address address=address_repo.save(req.getAddress());
		Restaurant restaurant=new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		restaurant.setOpen(true);
		
		return rest_repo.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		CreateRestaurantRequest req=updatedRestaurant;
		Address address=address_repo.save(req.getAddress());
		Restaurant restaurant1=new Restaurant();
		restaurant1.setAddress(address);
		restaurant1.setContactInformation(req.getContactInformation());
		restaurant1.setCuisineType(req.getCuisineType());
		restaurant1.setDescription(req.getDescription());
		restaurant1.setImages(req.getImages());
		restaurant1.setName(req.getName());
		restaurant1.setOpeningHours(req.getOpeningHours());
		restaurant1.setRegistrationDate(LocalDateTime.now());
		restaurant1.setOwner(restaurant.getOwner());
		
		return rest_repo.save(restaurant1);
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		if(restaurant!=null) {
			rest_repo.delete(restaurant);
		}
		
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		return rest_repo.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyWord) {
		
		return rest_repo.findBySearchQuery(keyWord);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		Optional<Restaurant> opt=rest_repo.findById(id);
		if(opt.isEmpty()) {
			throw new Exception("No restaurant Found with the given Id "+id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant=rest_repo.findByOwnerId(userId);
		if(restaurant==null) {
//			throw new Exception("Restaurant not found with OwnerId "+userId);
			return null;
		}

		return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		RestaurantDto dto=new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
		boolean isFavorited =false;
		List<RestaurantDto> favorites = user.getFavorites();
		for (RestaurantDto favorite:favorites) {
			if(favorite.getId().equals(restaurantId)) {
				isFavorited=true;
				break;
			}
		}
		
		if(isFavorited) {
			favorites.removeIf(fav->fav.getId().equals(restaurantId));
		}
		else {
			favorites.add(dto);
		}
		
		user_repo.save(user);
		
		return dto;
	} 

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		Restaurant restaurant=findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen()); 
		return rest_repo.save(restaurant);
	}

}
