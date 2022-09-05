package com.example.spring.batch.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class PeopleRecordMapper {

    @Bean
    public LineMapper<PeopleRecord> lineMapper() {
        DefaultLineMapper<PeopleRecord> mapper = new DefaultLineMapper<>();
        mapper.setFieldSetMapper(fieldSet -> PeopleRecord.builder().id(Long.parseLong(fieldSet.readString(0)))
                .name(fieldSet.readString(1))
                .birthDate(LocalDate.parse(fieldSet.readString(2), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .age(Integer.parseInt(fieldSet.readString(3)))
                .document(fieldSet.readString(4))
                .maleOrFemale(fieldSet.readString(5))
                .build());
        mapper.setLineTokenizer(new DelimitedLineTokenizer());
        return mapper;
    }
}
