package com.example.spring.data.controller.global;

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

import com.example.spring.data.entity.global.Person;
import com.example.spring.data.repository.global.PersonRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/people", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<Iterable<Person>> findAllPeople() {
        Iterable<Person> data = personRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> findByIdPeople(@PathVariable Long id) {
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }

}
