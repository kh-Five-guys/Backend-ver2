package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("board")
public class BoardDTO {
	private int boardNo;
	private String userId;
	private String userNick;
	private String boardTitle;
	private String boardContent;
	private int boardCount;
	private String boardWriteDate;
	private String boardUpdateDate;
	private int boardLike;
	private int boardHate;
	private int categorieNo;
	private String categorieName;
	private int commentCount;
	private String userProImg;
	
	
	public BoardDTO() {	}


	public int getBoardNo() {
		return boardNo;
	}


	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserNick() {
		return userNick;
	}


	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}


	public String getBoardTitle() {
		return boardTitle;
	}


	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}


	public String getBoardContent() {
		return boardContent;
	}


	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}


	public int getBoardCount() {
		return boardCount;
	}


	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}


	public String getBoardWriteDate() {
		return boardWriteDate;
	}


	public void setBoardWriteDate(String boardWriteDate) {
		this.boardWriteDate = boardWriteDate;
	}


	public String getBoardUpdateDate() {
		return boardUpdateDate;
	}


	public void setBoardUpdateDate(String boardUpdateDate) {
		this.boardUpdateDate = boardUpdateDate;
	}


	public int getBoardLike() {
		return boardLike;
	}


	public void setBoardLike(int boardLike) {
		this.boardLike = boardLike;
	}


	public int getBoardHate() {
		return boardHate;
	}


	public void setBoardHate(int boardHate) {
		this.boardHate = boardHate;
	}


	public int getCategorieNo() {
		return categorieNo;
	}


	public void setCategorieNo(int categorieNo) {
		this.categorieNo = categorieNo;
	}


	public String getCategorieName() {
		return categorieName;
	}


	public void setCategorieName(String categorieName) {
		this.categorieName = categorieName;
	}


	public int getCommentCount() {
		return commentCount;
	}


	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}


	public String getUserProImg() {
		return userProImg;
	}


	public void setUserProImg(String userProImg) {
		this.userProImg = userProImg;
	}


	@Override
	public String toString() {
		return "BoardDTO [boardNo=" + boardNo + ", userId=" + userId + ", userNick=" + userNick + ", boardTitle="
				+ boardTitle + ", boardContent=" + boardContent + ", boardCount=" + boardCount + ", boardWriteDate="
				+ boardWriteDate + ", boardUpdateDate=" + boardUpdateDate + ", boardLike=" + boardLike + ", boardHate="
				+ boardHate + ", categorieNo=" + categorieNo + ", categorieName=" + categorieName + ", commentCount="
				+ commentCount + ", userProImg=" + userProImg + "]";
	}

		
	
}
