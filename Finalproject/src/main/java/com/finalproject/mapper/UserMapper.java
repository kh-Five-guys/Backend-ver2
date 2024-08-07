package com.finalproject.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.finalproject.dto.UserDTO;

@Mapper
public interface UserMapper {
    UserDTO login(Map<String, Object> map);
    
    int insertUser(UserDTO userDTO);
    
    int updateUser(UserDTO userDTO);
    
    int deleteUser(String userId); // 회원 삭제

    int updateUserRank(String userId, int rankNo); // 회원 등급 변경
    
    List<UserDTO> getAllUsers();
    
    UserDTO findUserByEmail(String email);
    
    UserDTO findUserByIdAndEmail(@Param("userId") String userId, @Param("email") String email);

    int updateUserPassword(@Param("userId") String userId, @Param("tempPassword") String tempPassword);


    
    // 장원님 추가
	UserDTO getUserByUserName(String userName);

	UserDTO getUserInfo(String userId);
    
}