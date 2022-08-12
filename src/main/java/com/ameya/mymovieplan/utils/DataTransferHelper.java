package com.ameya.mymovieplan.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.entity.RoleEntity;
import com.ameya.mymovieplan.entity.UserEntity;
import com.ameya.mymovieplan.model.request.UserSignupRequestModel;
import com.ameya.mymovieplan.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransferHelper {
	
	
	@Autowired
	RoleRepository roleRepository;
	
	public UserDto userRequestToDto(UserSignupRequestModel user) {
		UserDto dto = new UserDto();
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		return dto;
	}
	
	public UserEntity userDtoToEntity(UserDto dto) {
		UserEntity user = new UserEntity();
		user.setId(dto.getId());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setUserId(dto.getUserId());
		user.setPhone(dto.getPhone());
		user.setEncryptedPassword(dto.getPassword());
		
		Collection<RoleEntity> roles = new HashSet<>();
		
		for(String r : dto.getRoles()) {
			RoleEntity role = roleRepository.findByName(r);
			if(role != null) {
				roles.add(role);
			}
		}
		
		user.setRoles(roles);
		
		return user;
	}

	public UserDto userEntityToDto(UserEntity savedUser) {
		UserDto user = new UserDto();
		user.setId(savedUser.getId());
		user.setFirstName(savedUser.getFirstName());
		user.setLastName(savedUser.getLastName());
		user.setEmail(savedUser.getEmail());
		user.setUserId(savedUser.getUserId());
		user.setPhone(savedUser.getPhone());
		Collection<RoleEntity> rolesEntity = savedUser.getRoles();
		Collection<String> roles = new ArrayList<>();
		for(RoleEntity r : rolesEntity) {
			roles.add(r.getName());
		}
		user.setRoles(roles);
		return user;
	}

}
