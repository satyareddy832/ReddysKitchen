package com.satya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.satya.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);
	

}
