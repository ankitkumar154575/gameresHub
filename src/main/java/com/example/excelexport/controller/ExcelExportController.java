package com.example.excelexport.controller;

import com.example.excelexport.service.EmailService;
import com.example.excelexport.service.ExcelExportService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/excel")
public class ExcelExportController {

    private final ExcelExportService excelExportService;
    private final EmailService emailService;

    public ExcelExportController(ExcelExportService excelExportService, EmailService emailService) {
        this.excelExportService = excelExportService;
        this.emailService = emailService;
    }

    @GetMapping("/generate")
    public String generateExcel() {
        return "Excel file generated at: " + excelExportService.exportToExcel();
    }

    @PostMapping("/send")
    public String sendExcelEmail(@RequestParam String email) {
        try {
            String filePath = excelExportService.exportToExcel();
            emailService.sendEmailWithAttachment(email, filePath);
            return "Email sent successfully to " + email;
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}