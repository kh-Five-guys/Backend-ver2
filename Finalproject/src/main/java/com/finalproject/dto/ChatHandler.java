package com.finalproject.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository repository;

    public ChatHandler(ObjectMapper objectMapper, ChatRoomRepository repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection opened: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received payload: " + payload);

        // JSON 파싱
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        String roomId = chatMessage.getChatRoomId();
        
        ChatRoomDTO chatRoom = repository.getChatRoom(roomId);
        if (chatRoom == null) {
            System.out.println("Chat room not found: " + roomId);
            return;
        }
        
        // 메시지 처리
        chatRoom.handleMessage(session, chatMessage, objectMapper);
    }
    
    public void send(ChatMessage chatMessage, ObjectMapper objectMapper, String userName, LocalDateTime regDate) throws JsonProcessingException {
        // 클라이언트에게 메시지 전송 준비
        ChatMessage responseMessage = new ChatMessage();
        responseMessage.setChatRoomId(chatMessage.getChatRoomId());
        responseMessage.setType("SEND");
        responseMessage.setMessage(chatMessage.getMessage());
        responseMessage.setUserName(userName); // 사용자 이름 설정
        responseMessage.setRegDate(regDate); // 메시지 보낸 시간 설정

   
        }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed: " + session.getId() + " with status: " + status);
        repository.remove(session);
    }
}
