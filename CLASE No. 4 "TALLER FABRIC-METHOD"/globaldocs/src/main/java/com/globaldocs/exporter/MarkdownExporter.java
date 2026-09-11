package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MarkdownExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        Path path = Path.of(outputPath);
        Files.createDirectories(path.getParent());
        String mdContent = "# Documento Procesado\n\n" + content;
        Files.writeString(path, mdContent);
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.MARKDOWN;
    }
}
