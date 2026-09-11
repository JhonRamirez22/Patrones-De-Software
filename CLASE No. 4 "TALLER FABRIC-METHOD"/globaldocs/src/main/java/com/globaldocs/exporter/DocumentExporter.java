package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;

import java.io.IOException;

public interface DocumentExporter {
    void export(String content, String outputPath) throws IOException;
    DocumentFormat getFormat();
}
