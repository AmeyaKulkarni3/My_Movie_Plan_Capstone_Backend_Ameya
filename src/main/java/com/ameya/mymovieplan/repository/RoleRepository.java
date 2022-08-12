package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.RoleEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Integer>{
	
	public RoleEntity findByName(String name);

}
