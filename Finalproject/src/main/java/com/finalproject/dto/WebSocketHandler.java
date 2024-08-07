package com.finalproject.dto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 세션 저장
        String roomId = getRoomIdFromSession(session); // 세션에서 roomId 추출 방법에 따라 구현
        sessions.put(roomId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 제거
        String roomId = getRoomIdFromSession(session); // 세션에서 roomId 추출 방법에 따라 구현
        sessions.remove(roomId);
    }
    
    public WebSocketSession getSession(String roomId) {
        return sessions.get(roomId);
    }

    private String getRoomIdFromSession(WebSocketSession session) {
        // 세션에서 roomId를 추출하는 방법을 구현
        return (String) session.getAttributes().get("roomId");
    }
}
