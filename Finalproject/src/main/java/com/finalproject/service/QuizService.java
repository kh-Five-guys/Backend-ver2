package com.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.dto.QuizDTO;
import com.finalproject.mapper.QuizMapper;


@Service
public class QuizService {
	
	@Autowired
	private QuizMapper mapper;

	
	public List<QuizDTO> getRandomQuizzes(int quizCnt) {
        return mapper.getRandomQuizzes(quizCnt);
    }
}
