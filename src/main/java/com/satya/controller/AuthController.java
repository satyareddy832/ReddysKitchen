package com.satya.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.satya.config.JwtProvider;
import com.satya.model.Cart;
import com.satya.model.USER_ROLE;
import com.satya.model.User;
import com.satya.repo.CartRepo;
import com.satya.repo.UserRepo;
import com.satya.request.LoginRequest;
import com.satya.response.AuthResponse;
import com.satya.service.CustomerUserDetailsService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepo user_repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private CustomerUserDetailsService customerUserDetailsService;
	
	@Autowired
	private CartRepo cart_repo;
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		
		User isEmailExist=user_repo.findByEmail(user.getEmail());
		if(isEmailExist!=null) {
			throw new Exception("User is already registered with email "+user.getEmail());
		}
		
		String email=user.getEmail();
		String password=user.getPassword();
		
		User createdUser =new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser=user_repo.save(createdUser);
		
		Cart cart=new Cart();
		cart.setCustomer(savedUser);
		cart_repo.save(cart);
		
		
//		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		
//		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
//		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority(); 
//		
//		String jwt=jwtProvider.generateToken(authentication);
//		
//		
//		AuthResponse authResponse=new AuthResponse();
//		authResponse.setJwt(jwt);
//		authResponse.setMessage("Registration Success");
//		authResponse.setRole(savedUser.getRole());
		Authentication authentication=authenticate(email,password); 
		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority(); 

		String jwt=jwtProvider.generateToken(authentication);
		
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		
		
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){
		
		String email=req.getEmail();
		String password=req.getPassword();
		
		Authentication authentication=authenticate(email,password); 
		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority(); 

		String jwt=jwtProvider.generateToken(authentication);
		
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}


	private Authentication authenticate(String email, String password) {
		
		UserDetails userDetails=customerUserDetailsService.loadUserByUsername(email);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid Username....");
		}
		
		if(passwordEncoder.matches(password, userDetails.getPassword())==false) {
			throw new BadCredentialsException("Invalid Password....");
		}
			
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
		
	}
	
	
	
	
}
