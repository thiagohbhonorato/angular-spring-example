package com.example.spring.batch.processor;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.entity.People;
import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class ItemProcessorFileToDatabase {

    private ModelMapper modelMapper;

    public ItemProcessorFileToDatabase(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Bean
    @StepScope
    Function<PeopleRecord, People> processFileToDatabase(
            @Value("#{jobParameters['" + Constants.JOB_PARAM_PROCESSING_KEY + "']}") String processingKey) {
        return (peopleRecord) -> {
            People people = modelMapper.map(peopleRecord, People.class);
            people.setProcessingKey(processingKey);
            return people;
        };
    }
}
