package com.example.spring.data.repository.global;

import org.springframework.data.repository.CrudRepository;

import com.example.spring.data.entity.global.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
