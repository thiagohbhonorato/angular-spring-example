package com.example.spring.batch.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.batch.entity.People;

public interface PeopleRepository extends CrudRepository<People, Long> {

}
