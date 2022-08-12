package com.ameya.mymovieplan;

import java.util.Arrays;
import java.util.Collection;

import com.ameya.mymovieplan.entity.AuthorityEntity;
import com.ameya.mymovieplan.entity.RoleEntity;
import com.ameya.mymovieplan.entity.UserEntity;
import com.ameya.mymovieplan.repository.AuthorityRepository;
import com.ameya.mymovieplan.repository.RoleRepository;
import com.ameya.mymovieplan.repository.UserRepository;
import com.ameya.mymovieplan.utils.Role;
import com.ameya.mymovieplan.utils.UserIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IntialUserSetup {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserIdGenerator uIdGen;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		
		createRole(Role.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
		RoleEntity roleAdmin = createRole(Role.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority,deleteAuthority));
		
		if(roleAdmin == null) return;
		
		UserEntity user = new UserEntity();
		
		UserEntity saved = userRepository.findByEmail("admin@1");
		
		if(saved == null) {
			user.setFirstName("Admin");
			user.setLastName("User1");
			user.setEmail("admin@1");
			user.setPhone("");
			user.setUserId(uIdGen.generateUserId(30));
			user.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
			user.setRoles(Arrays.asList(roleAdmin));
			userRepository.save(user);
		}
	}
	
	@Transactional
	private AuthorityEntity createAuthority(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if(authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}
	
	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities){
		RoleEntity role = roleRepository.findByName(name);
		if(role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}

}
