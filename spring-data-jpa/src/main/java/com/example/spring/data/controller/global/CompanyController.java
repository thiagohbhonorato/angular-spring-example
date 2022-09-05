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

import com.example.spring.data.entity.global.Company;
import com.example.spring.data.repository.global.CompanyRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/companies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public ResponseEntity<Iterable<Company>> findAllPeople() {
        Iterable<Company> data = companyRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{id}")
    public ResponseEntity<Company> findByIdPeople(@PathVariable Long id) {
        Optional<Company> optional = companyRepository.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
        }
    }

}
