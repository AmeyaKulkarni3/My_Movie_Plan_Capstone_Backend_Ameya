package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.AuthorityEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<AuthorityEntity, Integer> {
	
	public AuthorityEntity findByName(String name);

}
