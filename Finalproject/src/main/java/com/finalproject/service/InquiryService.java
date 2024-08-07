package com.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finalproject.dto.InquiryDTO;
import com.finalproject.mapper.InquiryMapper;

@Service
public class InquiryService {
    private InquiryMapper mapper;

    public InquiryService(InquiryMapper mapper) {
        this.mapper = mapper;
    }

    public int insertInquiry(InquiryDTO inquiryDTO) {
        return mapper.insertInquiry(inquiryDTO);
    }

    public List<InquiryDTO> getAllInquiries() {
        return mapper.selectAllInquiries();
    }
}