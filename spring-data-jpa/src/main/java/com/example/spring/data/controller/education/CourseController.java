package com.example.spring.data.controller.education;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring.data.entity.education.Course;
import com.example.spring.data.repository.education.CourseRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/courses", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity<Iterable<Course>> findAllPeople() {
        Iterable<Course> data = courseRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> findByIdPeople(@PathVariable Long id) {
        Optional<Course> optional = courseRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }

}
