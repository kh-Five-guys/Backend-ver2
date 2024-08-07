package com.finalproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.dto.UserDTO;
import com.finalproject.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class GetUserController {
    private final UserService userService;

    public GetUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable String userId) {
        UserDTO user = userService.getUserInfo(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 로그인한 사용자 정보를 가져오기 위해 세션에서 사용자 ID를 추출하여 /user/{userId}로 호출하는 메서드 추가
    @GetMapping("/user/me")
    public ResponseEntity<UserDTO> getCurrentUserInfo(HttpSession session) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(currentUser);
    }
    
    @GetMapping("/user/{userName}")
    public ResponseEntity<UserDTO> getUserInfoByUserName(@PathVariable String userName) {
        UserDTO user = userService.getUserByUserName(userName);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}

