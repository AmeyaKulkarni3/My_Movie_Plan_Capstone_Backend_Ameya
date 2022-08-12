package com.ameya.mymovieplan.controller;

import java.util.Arrays;
import java.util.HashSet;

import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.exception.UserAlreadyExistsException;
import com.ameya.mymovieplan.model.request.UserSignupRequestModel;
import com.ameya.mymovieplan.service.UserService;
import com.ameya.mymovieplan.utils.DataTransferHelper;
import com.ameya.mymovieplan.utils.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private DataTransferHelper helper;

	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserSignupRequestModel user) throws UserAlreadyExistsException {

		UserDto dto = helper.userRequestToDto(user);
		dto.setRoles(new HashSet<>(Arrays.asList(Role.ROLE_USER.name())));
		return ResponseEntity.ok(userService.createUser(dto));
	}
	
	@GetMapping("/users")
	public ResponseEntity<String> getAllUsers(){
		return ResponseEntity.ok("In Get all users");
	}

}
