package com.finalproject.service;

import com.finalproject.dto.PrivateChatDTO;
import com.finalproject.mapper.PrivateChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateChatService {

    @Autowired
    private PrivateChatMapper privateChatMapper;

    public void sendMessage(String senderId, String receiverId, String messageContent) {
        privateChatMapper.insertMessage(senderId, receiverId, messageContent);
    }

    public List<PrivateChatDTO> getConversation(String userId1, String userId2) {
        return privateChatMapper.selectConversation(userId1, userId2);
    }
}
