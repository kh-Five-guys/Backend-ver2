package com.finalproject.dto;

import java.util.Date;

public class FriendDTO {
    private String userId1;
    private String userId2;
    private Date friendshipDate;

    // 기본 생성자
    public FriendDTO() {}

    // 모든 필드를 포함하는 생성자
    public FriendDTO(String userId1, String userId2, Date friendshipDate) {
		super();
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.friendshipDate = friendshipDate;
	}

    // getter 및 setter
    public String getUserId1() {
        return userId1;
    }


	public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }
    public Date getFriendshipDate() {
        return friendshipDate;
    }

    public void setFriendshipDate(Date friendshipDate) {
        this.friendshipDate = friendshipDate;
    }
}
