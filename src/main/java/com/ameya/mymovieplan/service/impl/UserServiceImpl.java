package com.ameya.mymovieplan.service.impl;

import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.entity.UserEntity;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.UserAlreadyExistsException;
import com.ameya.mymovieplan.repository.RoleRepository;
import com.ameya.mymovieplan.repository.UserRepository;
import com.ameya.mymovieplan.security.UserPrincipal;
import com.ameya.mymovieplan.service.UserService;
import com.ameya.mymovieplan.utils.DataTransferHelper;
import com.ameya.mymovieplan.utils.UserIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:ValidationMessages.properties")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	Environment env;

	@Autowired
	UserIdGenerator uIdGen;

	@Autowired
	DataTransferHelper helper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto user) throws UserAlreadyExistsException {

		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExistsException(env.getProperty(ExceptionConstants.USER_ALREADY_EXISTS.toString()));
		}

		user.setUserId(uIdGen.generateUserId(30));
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		UserEntity savedUser = userRepository.save(helper.userDtoToEntity(user));

		UserDto returnValue = helper.userEntityToDto(savedUser);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(env.getProperty(ExceptionConstants.USER_NOT_FOUND.toString()));
		}
		
		return new UserPrincipal(user);

//		return new User(user.getEmail(), user.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {

		UserEntity user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(env.getProperty(ExceptionConstants.USER_NOT_FOUND.toString()));
		}

		UserDto returnValue = helper.userEntityToDto(user);
		return returnValue;
	}

}
