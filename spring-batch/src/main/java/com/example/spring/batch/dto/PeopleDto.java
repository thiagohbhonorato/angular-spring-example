package com.example.spring.batch.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String document;
    private String maleOrFemale;

}
