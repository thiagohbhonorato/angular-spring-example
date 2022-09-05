package com.example.spring.batch.exception;

public class JobExecutionRuntimeException extends RuntimeException {

    public JobExecutionRuntimeException() {
    }

    public JobExecutionRuntimeException(String message) {
        super(message);
    }

    public JobExecutionRuntimeException(Throwable cause) {
        super(cause);
    }

    public JobExecutionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobExecutionRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
