package com.finalproject.mapper;

import com.finalproject.dto.PrivateChatDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrivateChatMapper {

    // 메시지 추가
    void insertMessage(@Param("senderId") String senderId, @Param("receiverId") String receiverId, @Param("messageContent") String messageContent);

    // 대화 내용 조회
    List<PrivateChatDTO> selectConversation(@Param("userId1") String userId1, @Param("userId2") String userId2);
}
