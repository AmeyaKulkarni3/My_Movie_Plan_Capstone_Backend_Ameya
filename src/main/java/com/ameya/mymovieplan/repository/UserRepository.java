package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);
}
