package com.example.spring.batch.writer;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.entity.People;

@Configuration
public class ItemWriterFileToDatabase {

    private EntityManagerFactory batchEntityManagerFactory;

    public ItemWriterFileToDatabase(
            @Qualifier(Constants.BATCH_ENTITY_MANAGER_FACTORY) EntityManagerFactory batchEntityManagerFactory) {
        this.batchEntityManagerFactory = batchEntityManagerFactory;
    }

    @Bean
    @StepScope
    public JpaItemWriter<People> writerFileToDatabase() {
        JpaItemWriter<People> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(batchEntityManagerFactory);
        return writer;
    }
}
