package com.example.spring.batch.record;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeopleRecord {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Integer age;
    private String document;
    private String maleOrFemale;
    private Date processingDate;
}
