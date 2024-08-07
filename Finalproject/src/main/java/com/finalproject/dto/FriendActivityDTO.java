package com.finalproject.dto;

import java.sql.Timestamp;

public class FriendActivityDTO {
    private String userId;
    private String status;
    private Timestamp lastUpdate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "FriendActivityDTO{" +
                "userId='" + userId + '\'' +
                ", status='" + status + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
