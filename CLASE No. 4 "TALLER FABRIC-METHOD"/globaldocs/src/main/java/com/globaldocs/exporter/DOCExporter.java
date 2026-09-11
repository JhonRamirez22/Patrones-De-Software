package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DOCExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        Path path = Path.of(outputPath);
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        StringBuilder rtf = new StringBuilder();
        rtf.append("{\\rtf1\\ansi\\deff0\n");
        rtf.append("{\\fonttbl{\\f0 Courier New;}}\n");
        rtf.append("\\f0\\fs20\n");
        String[] lines = content.split("\n", -1);
        for (int i = 0; i < lines.length; i++) {
            if (i > 0) {
                rtf.append("\\par\n");
            }
            String escaped = lines[i]
                .replace("\\", "\\\\")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace("\r", "");
            rtf.append(escaped);
        }
        rtf.append("\n}");
        Files.writeString(path, rtf.toString());
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.DOC;
    }
}
