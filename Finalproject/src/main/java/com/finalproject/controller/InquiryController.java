package com.finalproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.dto.InquiryDTO;
import com.finalproject.service.InquiryService;

@RestController
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping("/inquiries")
    public ResponseEntity<String> submitInquiry(@RequestBody InquiryDTO inquiryDTO) {
        try {
            inquiryService.insertInquiry(inquiryDTO);
            return ResponseEntity.ok("문의사항이 성공적으로 제출되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의사항 제출 실패: " + e.getMessage());
        }
    }

    @GetMapping("/inquiries/member")
    public ResponseEntity<List<InquiryDTO>> getAllInquiries() {
        try {
            List<InquiryDTO> inquiries = inquiryService.getAllInquiries();
            return ResponseEntity.ok(inquiries);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}