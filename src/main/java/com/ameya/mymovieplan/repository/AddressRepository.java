package com.ameya.mymovieplan.repository;

import com.ameya.mymovieplan.entity.Address;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {

}
