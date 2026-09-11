package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;

public class DOCXExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        java.io.File parentDir = new java.io.File(outputPath).getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (XWPFDocument document = new XWPFDocument()) {
            String[] lines = content.split("\n");
            for (String line : lines) {
                XWPFParagraph paragraph = document.createParagraph();
                paragraph.createRun().setText(line);
            }
            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                document.write(out);
            }
        }
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.DOCX;
    }
}
