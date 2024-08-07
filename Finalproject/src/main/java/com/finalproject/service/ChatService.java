package com.finalproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finalproject.dto.ChatRoomRepository;
import com.finalproject.dto.UserDTO;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<UserDTO> getParticipantsByRoomId(String roomId) {
        // roomId에 해당하는 참여자 목록을 가져오는 로직 구현
        return chatRoomRepository.findParticipantsByRoomId(roomId);
    }
}
