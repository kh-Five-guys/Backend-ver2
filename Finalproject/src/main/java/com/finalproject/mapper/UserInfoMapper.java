package com.finalproject.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.finalproject.dto.UserDTO;

@Mapper
public interface UserInfoMapper {
	List<UserDTO> selectAllUser();
	
	List<UserDTO> selectgetUserByUserName(@Param("roomId") String roomName);

}
