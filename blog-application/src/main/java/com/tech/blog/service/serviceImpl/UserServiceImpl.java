package com.tech.blog.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tech.blog.config.AppConstants;
import com.tech.blog.entity.Role;
import com.tech.blog.entity.User;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.payload.UserDto;
import com.tech.blog.repository.RoleRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
		User savedUser = this.userRepository.save(user);
		
		return this.userToDto(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		User updatedUser = this.userRepository.save(user);
		UserDto dtoUser = this.userToDto(updatedUser);
		
		return dtoUser;
	}

	@Override
	public UserDto getUserById(Long userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepository.findAll();
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Long userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		this.userRepository.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		return user;
	}
	
	public UserDto userToDto(User user) {
		
		UserDto userDto= this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto registerUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		//password encoded
		user.setPassword(encoder.encode(user.getPassword()));
		
		Role role = this.roleRepository.findById(AppConstants.ROLE_USER).get();
		
		user.getRoles().add(role);
		
		User registeredUser = this.userRepository.save(user);
		
		return this.modelMapper.map(registeredUser, UserDto.class);
	}
	
	
}
