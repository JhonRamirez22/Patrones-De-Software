package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MarkdownExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsMarkdownFile() throws IOException {
        DocumentExporter exporter = new MarkdownExporter();
        Path output = tempDir.resolve("test.md");
        exporter.export("Some content", output.toString());
        assertTrue(Files.exists(output));
        String content = Files.readString(output);
        assertTrue(content.contains("# Documento Procesado"));
        assertTrue(content.contains("Some content"));
    }

    @Test
    void getFormatReturnsMARKDOWN() {
        assertEquals(DocumentFormat.MARKDOWN, new MarkdownExporter().getFormat());
    }
}
