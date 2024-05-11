package com.satya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.satya.config.JwtProvider;
import com.satya.model.User;
import com.satya.repo.UserRepo;


@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepo user_repo;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		User user=findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		User user=user_repo.findByEmail(email);
		if(user==null) {
			throw new Exception("user not found");
		}
		
		return user;
	}

}
