package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XLSXExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsXlsxFile() throws IOException {
        DocumentExporter exporter = new XLSXExporter();
        Path output = tempDir.resolve("test.xlsx");
        exporter.export("Report data", output.toString());
        assertTrue(Files.exists(output));
        assertTrue(Files.size(output) > 0);
        byte[] bytes = Files.readAllBytes(output);
        assertEquals((byte) 'P', bytes[0], "XLSX should start with PK (zip header)");
        assertEquals((byte) 'K', bytes[1]);
    }

    @Test
    void getFormatReturnsXLSX() {
        assertEquals(DocumentFormat.XLSX, new XLSXExporter().getFormat());
    }
}
