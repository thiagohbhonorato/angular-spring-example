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

import com.example.spring.data.entity.education.University;
import com.example.spring.data.repository.education.UniversityRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/universities", produces = MediaType.APPLICATION_JSON_VALUE)
public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping
    public ResponseEntity<Iterable<University>> findAllPeople() {
        Iterable<University> data = universityRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<University> findByIdPeople(@PathVariable Long id) {
        Optional<University> optional = universityRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University not found");
        }
    }

}
