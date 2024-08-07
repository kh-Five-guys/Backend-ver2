package com.finalproject.dto;

import org.apache.ibatis.type.Alias;

@Alias("user")
public class UserDTO {
	private String userId;
    private String userEmail;
    private String userPasswd;
    private String userNick;
    private String userProImg;
    private String userAddress;
    private Double latitude;  // 위도
    private Double longitude; // 경도
    private Integer rankNo;
    
	public UserDTO() {	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public String getUserProImg() {
		return userProImg;
	}

	public void setUserProImg(String userProImg) {
		this.userProImg = userProImg;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getRankNo() {
		return rankNo;
	}

	public void setRankNo(Integer rankNo) {
		this.rankNo = rankNo;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userEmail=" + userEmail + ", userPasswd=" + userPasswd + ", userNick="
				+ userNick + ", userProImg=" + userProImg + ", userAddress=" + userAddress + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", rankNo=" + rankNo + "]";
	}
	
	
	

}