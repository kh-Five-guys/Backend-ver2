package com.finalproject.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finalproject.dto.UserDTO;
import com.finalproject.service.UserInfoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserInfoController {
    private final UserInfoService service;

    public UserInfoController(UserInfoService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public List<UserDTO> selectAllUser() {
        return service.selectAllUser();
    }
}