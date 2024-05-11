package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
