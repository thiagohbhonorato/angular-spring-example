package com.example.spring.batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.dto.PeopleDto;
import com.example.spring.batch.processor.ItemProcessorDatabaseToFile;
import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class StepDatabaseToFile {

    private StepBuilderFactory stepBuilderFactory;

    public StepDatabaseToFile(StepBuilderFactory stepBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    @Qualifier(Constants.STEP_DATABESE_TO_FILE)
    Step createStepDatabaseToFile(JdbcCursorItemReader<PeopleDto> reader,
            ItemProcessorDatabaseToFile processor,
            FlatFileItemWriter<PeopleRecord> writer) {
        return this.stepBuilderFactory.get(Constants.STEP_DATABESE_TO_FILE)
                .<PeopleDto, PeopleRecord>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
