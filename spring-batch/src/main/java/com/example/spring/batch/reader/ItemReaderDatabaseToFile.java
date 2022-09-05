package com.example.spring.batch.reader;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.config.QueriesEnvironment;
import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.dto.PeopleDto;
import com.example.spring.batch.mapper.PeopleDtoMapper;

@Configuration
public class ItemReaderDatabaseToFile {

    private DataSource dataSource;

    private QueriesEnvironment queries;

    public ItemReaderDatabaseToFile(DataSource dataSource, QueriesEnvironment queries) {
        this.dataSource = dataSource;
        this.queries = queries;
    }

    @Bean
    @StepScope
    JdbcCursorItemReader<PeopleDto> readerDatabaseToFile() {
        return new JdbcCursorItemReaderBuilder<PeopleDto>()
                .name(Constants.STEP_DATABESE_TO_FILE)
                .dataSource(dataSource)
                .sql(queries.getPeople())
                .saveState(true)
                .rowMapper(new PeopleDtoMapper())
                .build();
    }
}
