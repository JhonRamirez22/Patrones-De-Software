package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;

public class DocumentExporterFactory {
    public static DocumentExporter createExporter(DocumentFormat format) {
        return switch (format) {
            case TXT -> new PlainTextExporter();
            case CSV -> new CSVExporter();
            case MARKDOWN -> new MarkdownExporter();
            case PDF -> new PDFExporter();
            case DOCX -> new DOCXExporter();
            case DOC -> new DOCExporter();
            case XLSX -> new XLSXExporter();
        };
    }
}
