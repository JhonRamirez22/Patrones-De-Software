package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        Path path = Path.of(outputPath);
        Files.createDirectories(path.getParent());
        String csvContent = escapeCSV(content);
        Files.writeString(path, csvContent);
    }

    private String escapeCSV(String content) {
        if (content.contains(",") || content.contains("\"") || content.contains("\n")) {
            return "\"" + content.replace("\"", "\"\"") + "\"";
        }
        return content;
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.CSV;
    }
}
