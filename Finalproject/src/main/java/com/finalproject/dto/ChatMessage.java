package com.finalproject.dto;

import java.time.LocalDateTime;

public class ChatMessage {
    private String chatRoomId; // 채팅방 ID
    private String message; // 메시지 내용
    private String type; // 메시지 타입 (입장, 퇴장, 채팅, 가입 등)
    private String userName;
    private LocalDateTime regDate;

    // 기본 생성자
    public ChatMessage() {
    }

    // 빌더 패턴을 사용한 생성자
    public ChatMessage(String chatRoomId, String message, String type) {
        this.chatRoomId = chatRoomId;
        this.message = message;
        this.type = type;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	// Getter와 Setter
    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChatMessage [chatRoomId=" + chatRoomId + ", message=" + message + ", type=" + type + "]";
    }
}
