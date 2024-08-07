package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("comment")
public class CommentDTO {
    private int commentNo; // COMMENT_NO
    private String commentContent; // COMMENT_CONTENT
    private String commentWriteDate; // COMMENT_WRITE_DATE
    private String userId; // USER_ID
    private int boardNo; // BOARD_NO
    private int commentLike;
    private int commentHate;
    
    
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getCommentWriteDate() {
		return commentWriteDate;
	}
	public void setCommentWriteDate(String commentWriteDate) {
		this.commentWriteDate = commentWriteDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public int getCommentLike() {
		return commentLike;
	}
	public void setCommentLike(int commentLike) {
		this.commentLike = commentLike;
	}
	public int getCommentHate() {
		return commentHate;
	}
	public void setCommentHate(int commentHate) {
		this.commentHate = commentHate;
	}
	@Override
	public String toString() {
		return "CommentDTO [commentNo=" + commentNo + ", commentContent=" + commentContent + ", commentWriteDate="
				+ commentWriteDate + ", userId=" + userId + ", boardNo=" + boardNo + ", commentLike=" + commentLike
				+ ", commentHate=" + commentHate + "]";
	}
	


}
