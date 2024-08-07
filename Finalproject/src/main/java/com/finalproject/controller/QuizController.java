package com.finalproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.dto.QuizDTO;
import com.finalproject.service.QuizService;

@RestController
public class QuizController {
	private final QuizService service;
	
	private int quizCnt = 5; 
	
	public QuizController(QuizService service) {
		this.service = service;
	}





	@GetMapping("/randomquiz")
    public ResponseEntity<List<QuizDTO>> getRandomQuizzes() {
		System.out.println("진입확인이요");
        List<QuizDTO> randomQuizzes = service.getRandomQuizzes(quizCnt);
        System.out.println(randomQuizzes);
        return new ResponseEntity<>(randomQuizzes, HttpStatus.OK);
    }

}
