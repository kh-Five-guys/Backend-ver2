package com.finalproject.dto;

public class CreateChatRoomRequest {
    private String roomTitle;
    private String region;
    private String userId;
    private String passwd;

    // Getters and Setters
    public String getRoomTitle() { return roomTitle; }
    public void setRoomTitle(String roomTitle) { this.roomTitle = roomTitle; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }
}
