package com.example.spring.batch.constant;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Constant class");
    }

    public static final String JOB_PARAM_FILE_NAME = "fileName";
    public static final String JOB_PARAM_PROCESSING_KEY = "processingKey";

    public static final String JOB_DATABESE_TO_FILE = "databaseToFileJob";
    public static final String JOB_FILE_TO_DATABESE = "fileToDatabaseJob";

    public static final String STEP_DATABESE_TO_FILE = "databaseToFileStep";
    public static final String STEP_FILE_TO_DATABASE = "fileToDatabaseStep";

    public static final String BATCH_TRANSACTION_MANAGER = "batchTransactionManager";
    public static final String BATCH_DATABASE = "batchDataSource";
    public static final String BATCH_JPA_VENDOR_ADAPTER = "batchJpaVendorAdapter";
    public static final String BATCH_ENTITY_MANAGER_FACTORY = "batchEntityManagerFactory";

    public static final String PACKAGES_TO_SCAN = "com.example.spring.batch";

}
