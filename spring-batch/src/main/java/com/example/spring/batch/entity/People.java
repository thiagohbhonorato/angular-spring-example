package com.example.spring.batch.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "people")
@NoArgsConstructor
@AllArgsConstructor
public class People implements Serializable {

    @Id
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String document;
    private String maleOrFemale;
    private String processingKey;

}
