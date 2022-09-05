package com.example.spring.batch.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DatabaseToFileResponse extends BatchResponse {

    private String path;

    private String fileName;

    @Builder.Default
    private String method = "GET";

}
