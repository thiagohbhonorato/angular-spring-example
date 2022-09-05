package com.example.spring.batch.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.spring.batch.exception.JobExecutionRuntimeException;
import com.example.spring.batch.response.DatabaseToFileResponse;
import com.example.spring.batch.response.FileResponse;
import com.example.spring.batch.response.FileToDatabaseResponse;
import com.example.spring.batch.service.SpringBatchService;

@RestController
@RequestMapping("api/batch/v1")
public class SpringBatchController {

    @Autowired
    private SpringBatchService service;

    @Value("${file-path}")
    private String path;

    private static final String URI_FILE = "srv/file";

    private static final String URI_FILE_DOWNLOAD = URI_FILE + "/{fileName:.+}";

    @PostMapping("job/database-to-file")
    public ResponseEntity<DatabaseToFileResponse> runDatabaseToFile(UriComponentsBuilder uriBuilder) {
        try {
            DatabaseToFileResponse response = service.runDatabaseToFile();
            URI uri = uriBuilder.path("api/batch/v1/" + URI_FILE_DOWNLOAD).buildAndExpand(response.getFileName())
                    .toUri();
            response.setPath(uri.getPath());
            return ResponseEntity.created(uri).body(response);
        } catch (JobExecutionException | JobExecutionRuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(URI_FILE)
    public ResponseEntity<List<FileResponse>> getFiles() {
        List<FileResponse> files = Stream.of(new File(path).listFiles()).map(file -> {
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
        return ResponseEntity.ok(files);
    }

    @ResponseBody
    @GetMapping(URI_FILE_DOWNLOAD)
    public ResponseEntity<InputStreamResource> getFile(@PathVariable(required = true) String fileName) {
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(path + "/" + fileName);
            InputStream in = fileSystemResource.getInputStream();
            return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(new InputStreamResource(in));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("job/file-to-database/{fileName:.+}")
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
