package com.example.spring.batch.writer;

import java.nio.charset.Charset;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.record.PeopleRecord;

@Configuration
public class ItemWriterDatabaseToFile {

    @Value("${file-path}")
    private String path;

    @Bean
    @StepScope
    public FlatFileItemWriter<PeopleRecord> writerDatabaseToFile(
            @Value("#{jobParameters[" + Constants.JOB_PARAM_FILE_NAME + "]}") String fileName) {
        FlatFileItemWriter<PeopleRecord> writer = new FlatFileItemWriterBuilder<PeopleRecord>()
                .name("itemWriterDatabaseToFile")
                .resource(new FileSystemResource(path + "/" + fileName))
                .delimited()
                .delimiter(",")
                .names("id", "name", "birthDate", "age", "document", "maleOrFemale", "processingDate")
                .build();
        writer.setEncoding(Charset.defaultCharset().displayName());
        return writer;
    }

}
