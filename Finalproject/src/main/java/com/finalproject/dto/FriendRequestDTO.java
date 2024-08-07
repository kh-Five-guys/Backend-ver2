package com.finalproject.dto;

import java.sql.Timestamp;

public class FriendRequestDTO {
    private Long requestId;
    private String senderId;
    private String receiverId;
    private Timestamp requestDate;
    private String status;
    private String userProImg;
    private String userNick;

    // 기본 생성자
    public FriendRequestDTO() {}

    // 모든 필드를 포함하는 생성자
    public FriendRequestDTO(Long requestId, String senderId, String receiverId, Timestamp requestDate, 
                            String status, String userProImg, String userNick) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestDate = requestDate;
        this.status = status;
        this.userProImg = userProImg;
        this.userNick = userNick;
    }

    // getter 및 setter
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserProImg() {
        return userProImg;
    }

    public void setUserProImg(String userProImg) {
        this.userProImg = userProImg;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }
}
