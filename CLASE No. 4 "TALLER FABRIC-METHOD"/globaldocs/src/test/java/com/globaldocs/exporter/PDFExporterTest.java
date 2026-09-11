package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PDFExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsPdfFile() throws IOException {
        DocumentExporter exporter = new PDFExporter();
        Path output = tempDir.resolve("test.pdf");
        exporter.export("Invoice Line 1\nInvoice Line 2", output.toString());
        assertTrue(Files.exists(output));
        assertTrue(Files.size(output) > 0);
        byte[] bytes = Files.readAllBytes(output);
        assertEquals((byte) '%', bytes[0], "PDF should start with %");
    }

    @Test
    void getFormatReturnsPDF() {
        assertEquals(DocumentFormat.PDF, new PDFExporter().getFormat());
    }
}
