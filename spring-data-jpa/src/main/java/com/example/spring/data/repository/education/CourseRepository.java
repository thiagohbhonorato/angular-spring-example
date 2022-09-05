package com.example.spring.data.repository.education;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.education.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
