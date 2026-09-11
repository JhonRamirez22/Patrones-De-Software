package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DOCExporterTest {

    @TempDir
    Path tempDir;

    @Test
    void exportsDocFile() throws IOException {
        DocumentExporter exporter = new DOCExporter();
        Path output = tempDir.resolve("test.doc");
        exporter.export("Legacy document content", output.toString());
        assertTrue(Files.exists(output));
        assertTrue(Files.size(output) > 0);
    }

    @Test
    void getFormatReturnsDOC() {
        assertEquals(DocumentFormat.DOC, new DOCExporter().getFormat());
    }
}
