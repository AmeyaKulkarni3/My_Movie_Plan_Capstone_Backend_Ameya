package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.Genre;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
	
	Genre findByName(String name);

}
