package com.example.spring.data.repository.education;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.education.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
