package com.coderahul.restaurantlisting.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.coderahul.restaurantlisting.dto.RestaurantDTO;
import com.coderahul.restaurantlisting.entity.Restaurant;
import com.coderahul.restaurantlisting.mapper.RestaurantMapper;
import com.coderahul.restaurantlisting.repo.RestaurantRepo;

@Service
public class RestaurantService {
	
	@Autowired
	RestaurantRepo restaurantRepo;

	public List<RestaurantDTO> findAllRestaurants() {
		// TODO Auto-generated method stub
		List<Restaurant> restaurants = restaurantRepo.findAll();

		// map it to list of DTOs
		List<RestaurantDTO> restaurantDTOList = restaurants.stream()
				.map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
				.collect(Collectors.toList());
		
		return restaurantDTOList;
	}

	public RestaurantDTO addRestaurentInDB(RestaurantDTO restaurantDTO) {
		// TODO Auto-generated method stub

		Restaurant saveRestaurant = restaurantRepo
				.save(RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO));

		return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(saveRestaurant);
	}

	public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<Restaurant> restaurant=restaurantRepo.findById(id);
		if(restaurant.isPresent()) {
			return new ResponseEntity<RestaurantDTO>(RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant.get()), HttpStatus.OK);
		}
		
		
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	
	

}
