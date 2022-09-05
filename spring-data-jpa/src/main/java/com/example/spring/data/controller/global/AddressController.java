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

import com.example.spring.data.entity.global.Address;
import com.example.spring.data.repository.global.AddressRepository;

@RestController
@RequestMapping(value = "api/sdj/v1/adresses", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public ResponseEntity<Iterable<Address>> findAllAddress() {
        Iterable<Address> data = addressRepository.findAll();
        return ResponseEntity.ok(data);
    }

    @GetMapping("{postalCode}")
    public ResponseEntity<Address> findByIdAddress(@PathVariable String postalCode) {
        Optional<Address> optional = addressRepository.findById(postalCode);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found");
        }
    }

}
