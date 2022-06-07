package com.tech.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.blog.exception.LoginException;
import com.tech.blog.payload.JwtAuthRequest;
import com.tech.blog.payload.JwtAuthResponse;
import com.tech.blog.payload.UserDto;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.security.JwtTokenHelper;
import com.tech.blog.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest authRequest){
		
		this.authenticate(authRequest.getUsername(),authRequest.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequest.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
	}

	private void authenticate(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		try {
			
			this.authenticationManager.authenticate(authenticationToken);

		}catch (Exception e) {
			throw new LoginException("Invalid username and password !!");
		}
	
		
	}
	
	@PostMapping("/registerUser")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		
		UserDto registeredUser = this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
		
	}
}
