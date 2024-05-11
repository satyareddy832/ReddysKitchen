package com.satya.service;

import com.satya.model.User;

public interface IUserService {

	public User findUserByJwtToken(String jwt) throws Exception;
	
	
	public User findUserByEmail(String email) throws Exception;
}
