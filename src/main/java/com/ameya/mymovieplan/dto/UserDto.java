package com.ameya.mymovieplan.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class UserDto {
	
	private int id;
	private String firstName;
	private String lastName;
	private String userId;
	private String email;
	private String phone;
	private String password;
	private Collection<String> roles;

}
