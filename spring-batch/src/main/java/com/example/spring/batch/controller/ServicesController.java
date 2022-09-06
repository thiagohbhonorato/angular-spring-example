package com.example.spring.batch.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.spring.batch.constant.Constants;
import com.example.spring.batch.entity.People;
import com.example.spring.batch.response.FileResponse;
import com.example.spring.batch.service.SpringBatchService;

@RestController
@RequestMapping(Constants.URI_SERVICES)
public class ServicesController {

    @Autowired
    private SpringBatchService service;

    @Value("${file-path}")
    private String path;

    @GetMapping(Constants.URI_FILE)
    public ResponseEntity<List<FileResponse>> getFiles() {
        return ResponseEntity.ok(service.getFiles());
    }

    @DeleteMapping(Constants.URI_FILE)
    public ResponseEntity<Void> removeFiles() {
        boolean removed = service.removeFiles();
        if (removed) {
            return ResponseEntity.noContent().build();
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Some files were not removed");
    }

    @GetMapping(value = Constants.URI_FILE_DOWNLOAD, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getFile(@PathVariable(required = true) String fileName) {
        try {
            FileSystemResource fileSystemResource = new FileSystemResource(path + "/" + fileName);
            InputStream in = fileSystemResource.getInputStream();
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/people")
    public ResponseEntity<Iterable<People>> getListPeople() {
        return ResponseEntity.ok(service.getListPeople());
    }

    @DeleteMapping("/people")
    public ResponseEntity<Void> removePeople() {
        service.removePeople();
        return ResponseEntity.noContent().build();
    }

}
