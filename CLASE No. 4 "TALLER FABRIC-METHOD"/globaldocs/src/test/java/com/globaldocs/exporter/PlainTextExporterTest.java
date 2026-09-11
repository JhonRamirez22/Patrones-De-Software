package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PlainTextExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsTxtFile() throws IOException {
        DocumentExporter exporter = new PlainTextExporter();
        Path output = tempDir.resolve("test.txt");
        exporter.export("Hello World\nLine 2", output.toString());
        assertTrue(Files.exists(output));
        assertEquals("Hello World\nLine 2", Files.readString(output));
    }

    @Test
    void createsParentDirectories() throws IOException {
        DocumentExporter exporter = new PlainTextExporter();
        Path output = tempDir.resolve("sub/dir/file.txt");
        exporter.export("content", output.toString());
        assertTrue(Files.exists(output));
    }

    @Test
    void getFormatReturnsTXT() {
        assertEquals(DocumentFormat.TXT, new PlainTextExporter().getFormat());
    }
}
