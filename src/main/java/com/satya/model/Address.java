package com.satya.model;

import java.util.List;

import com.satya.request.CreateFoodRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String streetAddress;
    private String city;
    private String stateProvince;
    private String postalCode;
    private String country;
	
}
