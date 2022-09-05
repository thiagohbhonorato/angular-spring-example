package com.example.spring.batch.step;

import java.util.function.Function;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.entity.People;
import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class StepFileToDatabase {

    private StepBuilderFactory stepBuilderFactory;

    public StepFileToDatabase(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @Qualifier(Constants.STEP_FILE_TO_DATABASE)
    Step createStepFileToDatabase(FlatFileItemReader<PeopleRecord> reader,
            Function<PeopleRecord, People> processor,
            JpaItemWriter<People> writer) {
        return this.stepBuilderFactory.get(Constants.STEP_FILE_TO_DATABASE)
                .<PeopleRecord, People>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
