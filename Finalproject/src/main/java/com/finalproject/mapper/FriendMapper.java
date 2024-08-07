package com.finalproject.mapper;

import com.finalproject.dto.FriendRequestDTO;
import com.finalproject.dto.FriendDTO;
import com.finalproject.dto.FriendActivityDTO; // 추가
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {

    // 친구 요청 추가
    void insertFriendRequest(@Param("senderId") String senderId, @Param("receiverId") String receiverId);

    // 친구 요청 조회
    List<FriendRequestDTO> selectFriendRequestsByUser(@Param("userId") String userId);

    // 친구 추가
    void insertFriend(@Param("userId") String userId, @Param("friendId") String friendId);

    // 친구 요청 삭제
    void deleteFriendRequest(@Param("userId") String userId, @Param("requesterId") String requesterId);

    // 친구 삭제
    void deleteFriend(@Param("userId") String userId, @Param("friendId") String friendId);

    // 친구 목록 조회
    List<FriendDTO> selectFriendsByUser(@Param("userId") String userId);

    // 보낸 친구 요청 조회
    List<FriendRequestDTO> selectSentRequestsByUser(@Param("userId") String userId);

    // 친구 상태 조회 메서드 추가
    List<FriendActivityDTO> selectFriendStatusByUser(@Param("userId") String userId);
}
