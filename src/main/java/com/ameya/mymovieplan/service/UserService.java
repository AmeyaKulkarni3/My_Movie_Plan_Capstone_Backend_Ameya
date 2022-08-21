package com.ameya.mymovieplan.service;

import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.exception.UserAlreadyExistsException;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	
	public UserDto createUser(UserDto user) throws UserAlreadyExistsException;
	public UserDto getUser(String email);
	public UserDto getUserById(String userId);

}
