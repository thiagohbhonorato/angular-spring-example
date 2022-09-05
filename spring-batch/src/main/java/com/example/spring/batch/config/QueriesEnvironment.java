package com.example.spring.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties("queries")
@PropertySource(value = "classpath:queries.yml", factory = YamlPropertySourceFactory.class)
public class QueriesEnvironment {

    private String people;

}
