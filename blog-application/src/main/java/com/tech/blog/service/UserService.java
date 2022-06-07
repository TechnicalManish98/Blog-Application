package com.tech.blog.service;

import java.util.List;

import com.tech.blog.payload.UserDto;

public interface UserService {

	UserDto registerUser(UserDto userDto);
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto userDto,Long userId);
	
	UserDto getUserById(Long userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Long userId);

}
