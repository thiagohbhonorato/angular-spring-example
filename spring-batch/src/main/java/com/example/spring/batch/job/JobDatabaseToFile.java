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
public class JobDatabaseToFile {

    private JobBuilderFactory jobBuilderFactory;

    public JobDatabaseToFile(JobBuilderFactory jobBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    @Qualifier(Constants.JOB_DATABESE_TO_FILE)
    Job createJobDatabaseToFile(@Qualifier(Constants.STEP_DATABESE_TO_FILE) Step step) {
        return this.jobBuilderFactory.get(Constants.JOB_DATABESE_TO_FILE)
                .validator(validator())
                .start(step)
                .build();
    }

    private JobParametersValidator validator() {
        return new JobParametersValidator() {
            @Override
            public void validate(JobParameters parameters) throws JobParametersInvalidException {
                String fileName = parameters.getString(Constants.JOB_PARAM_FILE_NAME);
                if (StringUtils.isBlank(fileName)) {
                    throw new JobParametersInvalidException(
                            "The " + Constants.JOB_PARAM_FILE_NAME + " parameter is required.");
                }
            }
        };
    }

}
