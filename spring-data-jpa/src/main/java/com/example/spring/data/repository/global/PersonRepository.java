package com.example.spring.data.repository.global;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.global.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
