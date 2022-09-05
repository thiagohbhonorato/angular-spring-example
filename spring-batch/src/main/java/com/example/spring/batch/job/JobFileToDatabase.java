package com.example.spring.batch.job;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.batch.constant.Constants;

@Configuration
public class JobFileToDatabase {

    private JobBuilderFactory jobBuilderFactory;

    public JobFileToDatabase(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    @Qualifier(Constants.JOB_FILE_TO_DATABESE)
    Job createJobFileToDatabase(@Qualifier(Constants.STEP_FILE_TO_DATABASE) Step step) {
        return this.jobBuilderFactory.get(Constants.JOB_FILE_TO_DATABESE)
                .validator(validator())
                .start(step)
                .build();
    }

    private JobParametersValidator validator() {
        return new JobParametersValidator() {
            @Override
            public void validate(JobParameters parameters) throws JobParametersInvalidException {
                String processingKey = parameters.getString(Constants.JOB_PARAM_PROCESSING_KEY);
                String fileName = parameters.getString(Constants.JOB_PARAM_FILE_NAME);
                if (StringUtils.isBlank(processingKey)) {
                    throw new JobParametersInvalidException(
                            "The " + Constants.JOB_PARAM_PROCESSING_KEY + " parameter is required.");
                } else if (StringUtils.isBlank(fileName)) {
                    throw new JobParametersInvalidException(
                            "The " + Constants.JOB_PARAM_FILE_NAME + " parameter is required.");
                }
            }
        };
    }
}
