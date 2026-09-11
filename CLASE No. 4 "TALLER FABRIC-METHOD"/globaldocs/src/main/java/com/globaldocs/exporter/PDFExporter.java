package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.openpdf.text.Document;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        java.io.File parentDir = new java.io.File(outputPath).getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            String[] lines = content.split("\n");
            for (String line : lines) {
                document.add(new Paragraph(line));
            }
            document.close();
        } catch (Exception e) {
            throw new IOException("Error exporting PDF: " + e.getMessage(), e);
        }
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.PDF;
    }
}
