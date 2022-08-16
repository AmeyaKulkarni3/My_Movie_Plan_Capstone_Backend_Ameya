package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.Theater;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends CrudRepository<Theater, Integer> {
	
	Theater findByName(String name);

}
