package com.example.spring.batch.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.entity.People;
import com.example.spring.batch.repository.PeopleRepository;
import com.example.spring.batch.response.BatchResponse;
import com.example.spring.batch.response.DatabaseToFileResponse;
import com.example.spring.batch.response.FileResponse;
import com.example.spring.batch.response.FileToDatabaseResponse;

@Service
public class SpringBatchService {

    private JobLauncher jobLauncher;

    private Job jobDatabaseToFile;
    private Job jobFileToDatabase;

    @Autowired
    private PeopleRepository repository;

    @Value("${file-path}")
    private String path;

    public SpringBatchService(JobLauncher jobLauncher,
            @Qualifier(Constants.JOB_DATABESE_TO_FILE) Job jobDatabaseToFile,
            @Qualifier(Constants.JOB_FILE_TO_DATABESE) Job jobFileToDatabase) {
        this.jobLauncher = jobLauncher;
        this.jobDatabaseToFile = jobDatabaseToFile;
        this.jobFileToDatabase = jobFileToDatabase;
    }

    public DatabaseToFileResponse runDatabaseToFile() throws JobExecutionException {
        String fileName = String.valueOf(Calendar.getInstance().getTime().getTime()) + ".txt";
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(Constants.JOB_PARAM_FILE_NAME, new JobParameter(fileName));
        try {
            JobExecution jobExecution = jobLauncher.run(jobDatabaseToFile, new JobParameters(parameterMap));
            if (jobExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
                DatabaseToFileResponse response = DatabaseToFileResponse.builder().fileName(fileName).build();
                jobResult(response, jobExecution);
                return response;
            }
            throw new JobExecutionException("Failed to execute the " + Constants.JOB_DATABESE_TO_FILE + " process");
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getMessage());
        }
    }

    public FileToDatabaseResponse runFileToDatabase(String fileName) throws JobExecutionException {
        String processingKey = String.valueOf(Calendar.getInstance().getTime().getTime());
        Map<String, JobParameter> parameterMap = new HashMap<>();
        parameterMap.put(Constants.JOB_PARAM_FILE_NAME, new JobParameter(fileName));
        parameterMap.put(Constants.JOB_PARAM_PROCESSING_KEY, new JobParameter(processingKey));
        try {
            JobExecution jobExecution = jobLauncher.run(jobFileToDatabase, new JobParameters(parameterMap));
            if (jobExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
                FileToDatabaseResponse response = FileToDatabaseResponse.builder().processingKey(processingKey).build();
                jobResult(response, jobExecution);
                return response;
            }
            throw new JobExecutionException("Failed to execute the " + Constants.JOB_FILE_TO_DATABESE + " process");
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
            throw new JobExecutionException(e.getMessage());
        }
    }

    private BatchResponse jobResult(BatchResponse response, JobExecution jobExecution) {
        String status = jobExecution.getExitStatus().getExitCode().toLowerCase();
        response.setStatus(status.substring(0, 1).toUpperCase() + status.substring(1));
        response.setStartTime(jobExecution.getStartTime());
        response.setEndTime(jobExecution.getEndTime());
        return response;
    }

    public List<FileResponse> getFiles() {
        File[] listFiles = getListFiles();
        if (listFiles != null) {
            return Stream.of(listFiles).map(file -> {
                Date creationDate = null;
                try {
                    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    creationDate = new Date(attr.creationTime().toMillis());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return FileResponse.builder()
                        .name(file.getName())
                        .creationDate(creationDate)
                        .build();
            }).collect(Collectors.toList());

        } else {
            return new ArrayList<>(0);
        }
    }

    private File[] getListFiles() {
        return new File(path).listFiles();
    }

    public Iterable<People> getListPeople() {
        return repository.findAll();
    }

    public void removePeople() {
        repository.deleteAll();
    }

    public boolean removeFiles() {
        boolean allRemoved = true;
        for (File file : getListFiles()) {
            if (allRemoved) {
                allRemoved = file.delete();
            } else {
                file.delete();
            }
        }
        return allRemoved;
    }

}
