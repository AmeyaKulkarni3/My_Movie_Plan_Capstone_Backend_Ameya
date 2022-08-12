package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.Language;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {
	
	Language findByName(String name);

}
