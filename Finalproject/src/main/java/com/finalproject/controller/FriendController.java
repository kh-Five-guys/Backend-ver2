package com.finalproject.controller;

import com.finalproject.dto.FriendRequestDTO;
import com.finalproject.dto.FriendDTO;
import com.finalproject.dto.FriendActivityDTO; // 추가
import com.finalproject.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @PostMapping("/addFriendRequest")
    public String addFriendRequest(@RequestParam("senderId") String senderId,
                                   @RequestParam("receiverId") String receiverId) {
        try {
            friendService.addFriendRequest(senderId, receiverId);
            return "친구 요청이 성공적으로 전송되었습니다.";
        } catch (Exception e) {
            return "친구 요청 전송 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/acceptFriendRequest")
    public String acceptFriendRequest(@RequestParam("userId") String userId,
                                      @RequestParam("requesterId") String requesterId) {
        try {
            friendService.acceptFriendRequest(userId, requesterId);
            return "친구 요청을 수락했습니다.";
        } catch (Exception e) {
            return "친구 요청 수락 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/declineFriendRequest")
    public String declineFriendRequest(@RequestParam("userId") String userId,
                                       @RequestParam("requesterId") String requesterId) {
        try {
            friendService.declineFriendRequest(userId, requesterId);
            return "친구 요청을 거절했습니다.";
        } catch (Exception e) {
            return "친구 요청 거절 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/removeFriend")
    public String removeFriend(@RequestParam("userId") String userId,
                               @RequestParam("friendId") String friendId) {
        try {
            friendService.removeFriend(userId, friendId);
            return "친구가 성공적으로 삭제되었습니다.";
        } catch (Exception e) {
            return "친구 삭제 중 오류가 발생했습니다.";
        }
    }

    @GetMapping("/requests")
    public List<FriendRequestDTO> getFriendRequests(@RequestParam("userId") String userId) {
        return friendService.getFriendRequests(userId);
    }

    @GetMapping("/friends")
    public List<FriendDTO> getFriends(@RequestParam("userId") String userId) {
        return friendService.getFriends(userId);
    }

    @GetMapping("/sentRequests")
    public List<FriendRequestDTO> getSentRequests(@RequestParam("userId") String userId) {
        return friendService.getSentRequests(userId);
    }

    // 친구 상태 조회 API 추가
    @GetMapping("/status")
    public List<FriendActivityDTO> getFriendStatus(@RequestParam("userId") String userId) {
        return friendService.getFriendStatus(userId);
    }
}

