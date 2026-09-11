package com.globaldocs.model;

public enum DocumentFormat {
    PDF(".pdf"),
    DOC(".doc"),
    DOCX(".docx"),
    MARKDOWN(".md"),
    CSV(".csv"),
    TXT(".txt"),
    XLSX(".xlsx");

    private final String extension;

    DocumentFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static DocumentFormat fromFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name must not be null or blank");
        }
        String lower = fileName.toLowerCase();
        for (DocumentFormat format : values()) {
            if (lower.endsWith(format.extension)) {
                return format;
            }
        }
        throw new IllegalArgumentException("Unsupported file extension: " + fileName);
    }
}
