package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CSVExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsCsvFile() throws IOException {
        DocumentExporter exporter = new CSVExporter();
        Path output = tempDir.resolve("test.csv");
        exporter.export("col1,col2\nval1,val2", output.toString());
        assertTrue(Files.exists(output));
        String content = Files.readString(output);
        assertTrue(content.contains("col1,col2"));
        assertTrue(content.contains("val1,val2"));
    }

    @Test
    void escapesCommasInValues() throws IOException {
        DocumentExporter exporter = new CSVExporter();
        Path output = tempDir.resolve("test.csv");
        exporter.export("name\n\"hello, world\"", output.toString());
        String content = Files.readString(output);
        assertTrue(content.contains("\"\"hello, world\"\""));
    }

    @Test
    void getFormatReturnsCSV() {
        assertEquals(DocumentFormat.CSV, new CSVExporter().getFormat());
    }
}
