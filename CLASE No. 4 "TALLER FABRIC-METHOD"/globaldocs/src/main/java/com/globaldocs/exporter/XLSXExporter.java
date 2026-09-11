package com.globaldocs.exporter;

import com.globaldocs.model.DocumentFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class XLSXExporter implements DocumentExporter {

    @Override
    public void export(String content, String outputPath) throws IOException {
        java.io.File parentDir = new java.io.File(outputPath).getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Documento");
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i++) {
                XSSFRow row = sheet.createRow(i);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(lines[i]);
            }
            try (FileOutputStream out = new FileOutputStream(outputPath)) {
                workbook.write(out);
            }
        }
    }

    @Override
    public DocumentFormat getFormat() {
        return DocumentFormat.XLSX;
    }
}
