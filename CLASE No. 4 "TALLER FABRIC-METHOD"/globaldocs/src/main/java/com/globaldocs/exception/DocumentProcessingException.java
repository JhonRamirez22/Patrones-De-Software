package com.globaldocs.exception;

public class DocumentProcessingException extends RuntimeException {

    private final String fileName;
    private final String countryCode;

    public DocumentProcessingException(String message, String fileName, String countryCode) {
        super(message);
        this.fileName = fileName;
        this.countryCode = countryCode;
    }

    public DocumentProcessingException(String message, String fileName, String countryCode, Throwable cause) {
        super(message, cause);
        this.fileName = fileName;
        this.countryCode = countryCode;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
