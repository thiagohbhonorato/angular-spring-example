package com.example.spring.data.entity.global;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String segment;

    @ManyToOne
    @JoinColumn(name = "address_postal_code", referencedColumnName = "postalCode")
    private Address address;

    @ManyToMany
    @JoinTable(name = "COMPANY_EMPLOYEE", joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> employees;

}
