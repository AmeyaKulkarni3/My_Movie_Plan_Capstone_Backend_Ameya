package com.ameya.mymovieplan.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(nullable=false, length = 50)
	private String lastName;
	
	@Column(nullable=false, length = 50)
	private String userId;
	
	@Column(nullable=false)
	private String encryptedPassword;
	
	@Column(nullable=false, length = 120, unique = true)
	private String email;
	
	@Column(nullable=false, length = 15)
	private String phone;
	
	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", 
			joinColumns = @JoinColumn(name="users_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="roles_id", referencedColumnName = "id"))
	private Collection<RoleEntity> roles;

}
