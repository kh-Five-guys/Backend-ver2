package com.finalproject.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.finalproject.dto.UserDTO;
import com.finalproject.mapper.UserInfoMapper;

@Service
public class UserInfoService {
	private UserInfoMapper mapper;
	
	
	public UserInfoService(UserInfoMapper mapper) {
		this.mapper = mapper;
	}

	public List<UserDTO> selectAllUser() {
		return mapper.selectAllUser();
	}
	
	public List<UserDTO> getUserByUserName(String roomName) {
        return mapper.selectgetUserByUserName(roomName);
    }

}
