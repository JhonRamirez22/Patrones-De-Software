package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DocumentExporterFactoryTest {

    @Test
    void createsAllSupportedExporters() {
        for (DocumentFormat format : DocumentFormat.values()) {
            DocumentExporter exporter = DocumentExporterFactory.createExporter(format);
            assertNotNull(exporter, "Exporter should not be null for " + format);
            assertEquals(format, exporter.getFormat());
        }
    }

    @Test
    void exporterFactoryReturnsCorrectTypes() {
        assertInstanceOf(PlainTextExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.TXT));
        assertInstanceOf(CSVExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.CSV));
        assertInstanceOf(MarkdownExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.MARKDOWN));
        assertInstanceOf(PDFExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.PDF));
        assertInstanceOf(DOCXExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.DOCX));
        assertInstanceOf(DOCExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.DOC));
        assertInstanceOf(XLSXExporter.class, DocumentExporterFactory.createExporter(DocumentFormat.XLSX));
    }
}
