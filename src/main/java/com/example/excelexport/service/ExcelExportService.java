package com.example.excelexport.service;

import com.example.excelexport.entity.ReconRecord;
import com.example.excelexport.repository.ReconRecordRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ExcelExportService {

    private static final Logger LOGGER = Logger.getLogger(ExcelExportService.class.getName());
    private final ReconRecordRepository repository;

    @Value("${excel.filepath}")
    private String filePath;

    public ExcelExportService(ReconRecordRepository repository) {
        this.repository = repository;
    }

    public String exportToExcel() {
        List<ReconRecord> records = repository.findAll();

        if (records.isEmpty()) {
            LOGGER.warning("No records found to export!");
            return "⚠ No records found. Excel file not created.";
        }

        File file = new File(filePath);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Recon Records");
            createHeaderRow(sheet, workbook);

            int rowNum = 1;
            for (ReconRecord record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getId());
                row.createCell(1).setCellValue(record.getCmtItemId());
                row.createCell(2).setCellValue(record.getItemType());
                row.createCell(3).setCellValue(record.getCreatedUpdated().toString());
            }

            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }

            File parentDir = file.getParentFile();
            if (!parentDir.exists() && !parentDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + parentDir.getAbsolutePath());
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }

            LOGGER.info("✅ Excel file created successfully at: " + file.getAbsolutePath());
            return "✅ Excel file created successfully at: " + file.getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "❌ Failed to create Excel file", e);
            throw new RuntimeException("❌ Failed to create Excel file", e);
        }
    }

    private void createHeaderRow(Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        String[] columns = {"LSI_esop ID", "CMT Item ID", "Item Type", "Created_Updated"};

        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerStyle);
        }
    }
}
