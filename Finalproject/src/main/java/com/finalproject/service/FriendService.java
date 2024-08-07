package com.finalproject.service;

import com.finalproject.dto.FriendRequestDTO;
import com.finalproject.dto.FriendDTO;
import com.finalproject.dto.FriendActivityDTO; // 추가
import com.finalproject.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    public void addFriendRequest(String senderId, String receiverId) throws Exception {
        friendMapper.insertFriendRequest(senderId, receiverId);
    }

    public void acceptFriendRequest(String userId, String requesterId) throws Exception {
        // 친구 요청을 수락
        friendMapper.insertFriend(userId, requesterId);

        // 요청이 수락된 경우, 요청 목록에서 제거
        friendMapper.deleteFriendRequest(userId, requesterId);
    }

    public void declineFriendRequest(String userId, String requesterId) throws Exception {
        // 친구 요청 목록에서 제거
        friendMapper.deleteFriendRequest(userId, requesterId);
    }

    public void removeFriend(String userId, String friendId) throws Exception {
        friendMapper.deleteFriend(userId, friendId);
    }

    public List<FriendRequestDTO> getFriendRequests(String userId) {
        return friendMapper.selectFriendRequestsByUser(userId);
    }

    public List<FriendDTO> getFriends(String userId) {
        return friendMapper.selectFriendsByUser(userId);
    }

    public List<FriendRequestDTO> getSentRequests(String userId) {
        return friendMapper.selectSentRequestsByUser(userId);
    }

    // 친구 상태 조회 메서드 추가
    public List<FriendActivityDTO> getFriendStatus(String userId) {
        return friendMapper.selectFriendStatusByUser(userId);
    }
}
