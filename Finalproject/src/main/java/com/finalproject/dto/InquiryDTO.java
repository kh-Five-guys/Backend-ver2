package com.finalproject.dto;

public class InquiryDTO {
	private String id;
    private String userId;
    private String title;
    private String message;
    private String createdAt;
    
	public InquiryDTO(String id, String userId, String title, String message, String createAt) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.message = message;
		this.createdAt = createAt;
	}
	public InquiryDTO() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "InquiryDTO [id=" + id + ", userId=" + userId + ", title=" + title + ", message=" + message
				+ ", createdAt=" + createdAt + "]";
	}
    
    
    
	
    
}