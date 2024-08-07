package com.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.finalproject.dto.InquiryDTO;

@Mapper
public interface InquiryMapper {
    int insertInquiry(InquiryDTO inquiryDTO);
    List<InquiryDTO> selectAllInquiries();
}