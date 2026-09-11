package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DOCXExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsDocxFile() throws IOException {
        DocumentExporter exporter = new DOCXExporter();
        Path output = tempDir.resolve("test.docx");
        exporter.export("Contract line 1\nContract line 2", output.toString());
        assertTrue(Files.exists(output));
        assertTrue(Files.size(output) > 0);
        byte[] bytes = Files.readAllBytes(output);
        assertEquals((byte) 'P', bytes[0], "DOCX should start with PK (zip header)");
        assertEquals((byte) 'K', bytes[1]);
    }

    @Test
    void getFormatReturnsDOCX() {
        assertEquals(DocumentFormat.DOCX, new DOCXExporter().getFormat());
    }
}
