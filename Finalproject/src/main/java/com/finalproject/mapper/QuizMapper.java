package com.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.finalproject.dto.QuizDTO;

@Mapper
public interface QuizMapper {

	List<QuizDTO> getRandomQuizzes(int quizCnt); 
//	getRandomQuizzes
	
}
