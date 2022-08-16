package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.Tier;

import org.springframework.data.repository.CrudRepository;

public interface TierRepository extends CrudRepository<Tier, Integer>{
	
	Tier findByName(String name);

}
