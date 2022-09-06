package com.example.spring.batch.controller;

import java.net.URI;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.exception.JobExecutionRuntimeException;
import com.example.spring.batch.response.DatabaseToFileResponse;
import com.example.spring.batch.response.FileToDatabaseResponse;
import com.example.spring.batch.service.SpringBatchService;

@RestController
@RequestMapping("api/batch/v1/job")
public class JobsController {

    @Autowired
    private SpringBatchService service;

    @Value("${file-path}")
    private String path;

    @PostMapping("database-to-file")
    public ResponseEntity<DatabaseToFileResponse> runDatabaseToFile(UriComponentsBuilder uriBuilder) {
        try {
            DatabaseToFileResponse response = service.runDatabaseToFile();
            URI uri = uriBuilder.path(Constants.URI_SERVICES + Constants.URI_FILE_DOWNLOAD)
                    .buildAndExpand(response.getFileName())
                    .toUri();
            response.setPath(uri.getPath());
            return ResponseEntity.created(uri).body(response);
        } catch (JobExecutionException | JobExecutionRuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("file-to-database/{fileName:.+}")
    public ResponseEntity<FileToDatabaseResponse> runFileToDatabase(@PathVariable(required = true) String fileName,
            UriComponentsBuilder uriBuilder) {
        try {
            FileToDatabaseResponse response = service.runFileToDatabase(fileName);
            URI uri = uriBuilder.path("api/batch/v1/srv/file-to-database/{processKey}")
                    .buildAndExpand(response.getProcessingKey()).toUri();
            return ResponseEntity.created(uri).body(response);
        } catch (JobExecutionException | JobExecutionRuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
