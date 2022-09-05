package com.example.spring.data.repository.education;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.education.University;

public interface UniversityRepository extends CrudRepository<University, Long> {

}
