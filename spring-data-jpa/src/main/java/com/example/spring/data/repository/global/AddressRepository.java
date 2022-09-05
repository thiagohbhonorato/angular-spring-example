package com.example.spring.data.repository.global;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.global.Address;

public interface AddressRepository extends CrudRepository<Address, String> {

}
