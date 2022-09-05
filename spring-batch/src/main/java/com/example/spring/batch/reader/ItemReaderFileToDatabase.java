package com.example.spring.batch.reader;

import java.nio.charset.Charset;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class ItemReaderFileToDatabase {

    @Value("${file-path}")
    private String path;

    private LineMapper<PeopleRecord> lineMapper;

    public ItemReaderFileToDatabase(LineMapper<PeopleRecord> lineMapper) {
        this.lineMapper = lineMapper;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<PeopleRecord> readerFileToDatabase(
            @Value("#{jobParameters['" + Constants.JOB_PARAM_FILE_NAME + "']}") String fileName) {
        FlatFileItemReader<PeopleRecord> reader = new FlatFileItemReaderBuilder<PeopleRecord>()
                .name(Constants.STEP_FILE_TO_DATABASE)
                .resource(new FileSystemResource(path + "/" + fileName))
                .linesToSkip(0).lineMapper(lineMapper).build();
        reader.setEncoding(Charset.defaultCharset().displayName());
        return reader;
    }
}
