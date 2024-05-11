package com.satya.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.satya.model.USER_ROLE;
import com.satya.model.User;
import com.satya.repo.UserRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo user_repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user=user_repo.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email "+email);
		}
		
		USER_ROLE role=user.getRole();
		
		if(role==null) {role=USER_ROLE.ROLE_CUSTOMER;}
		
		List<GrantedAuthority> authorities=new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(role.toString()));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
		
	}
	
}
