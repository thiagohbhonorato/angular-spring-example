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

import com.example.spring.data.entity.education.Student;
import com.example.spring.data.repository.education.StudentRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Iterable<Student>> findAllPeople() {
        Iterable<Student> data = studentRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findByIdPeople(@PathVariable Long id) {
        Optional<Student> optional = studentRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
    }

}
