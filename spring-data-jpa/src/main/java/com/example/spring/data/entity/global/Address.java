package com.example.spring.data.entity.global;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Address implements Serializable {

    @Id
    private String postalCode;

    private String streetName;

    private String buildingNumber;

    private String complement;

    private String neighborhood;

    private String city;

    private String stateOrRegion;

    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Person> people;

    @JsonIgnore
    @OneToMany(mappedBy = "address")
    private List<Company> companies;

}
