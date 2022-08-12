package com.ameya.mymovieplan.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="roles")
@Data
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable = false, length=50)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<UserEntity> users;

	@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinTable(name = "roles_authorities", 
			joinColumns = @JoinColumn(name="roles_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name="authorities_id", referencedColumnName = "id"))
	private Collection<AuthorityEntity> authorities;
	
	public RoleEntity(String name) {
		this.name = name;
	}
	
	public RoleEntity() {
		
	}
}
