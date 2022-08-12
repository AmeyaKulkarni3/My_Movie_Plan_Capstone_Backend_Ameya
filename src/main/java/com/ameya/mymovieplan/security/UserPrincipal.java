package com.ameya.mymovieplan.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ameya.mymovieplan.entity.AuthorityEntity;
import com.ameya.mymovieplan.entity.RoleEntity;
import com.ameya.mymovieplan.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1682746322104067104L;
	
	UserEntity userEntity;

	public UserPrincipal(UserEntity user) {
		this.userEntity = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<AuthorityEntity> authorityEntities = new ArrayList<>();
		
		Collection<RoleEntity> roles = userEntity.getRoles();
		
		if(roles == null) return authorities;
		
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
			authorityEntities.addAll(role.getAuthorities());			
		});
		
		authorityEntities.forEach(authorityEntity -> {
			authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
		});
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return userEntity.getEncryptedPassword();
	}

	@Override
	public String getUsername() {
		
		return userEntity.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
