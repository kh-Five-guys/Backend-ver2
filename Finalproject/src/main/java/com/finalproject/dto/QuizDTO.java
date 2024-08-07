package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("quiz")
public class QuizDTO {
	private int questionId;
	private String questionText;
	private char correctAnswer;
	
	
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public char getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(char correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	@Override
	public String toString() {
		return "QuizDTO [questionId=" + questionId + ", questionText=" + questionText + ", correctAnswer="
				+ correctAnswer + "]";
	}
	
	
}
