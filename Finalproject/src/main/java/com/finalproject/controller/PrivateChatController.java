package com.finalproject.controller;

import com.finalproject.dto.PrivateChatDTO;
import com.finalproject.service.PrivateChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/private-chat")
public class PrivateChatController {

    @Autowired
    private PrivateChatService privateChatService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam("senderId") String senderId,
                              @RequestParam("receiverId") String receiverId,
                              @RequestParam("messageContent") String messageContent) {
        try {
            privateChatService.sendMessage(senderId, receiverId, messageContent);
            return "메시지가 성공적으로 전송되었습니다.";
        } catch (Exception e) {
            return "메시지 전송 중 오류가 발생했습니다.";
        }
    }

    @GetMapping("/conversation")
    public List<PrivateChatDTO> getConversation(@RequestParam String userId1, @RequestParam String userId2) {
        return privateChatService.getConversation(userId1, userId2);
    }
}
