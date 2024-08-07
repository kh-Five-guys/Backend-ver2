package com.finalproject.dto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatRoomDTO {
    private String id;
    private String userId;
    private String roomTitle;
    private String passwd;
    private LocalDateTime regDate;
    private String region;
    private List<UserDTO> participants = new ArrayList<>(); // 초기화
    private Set<WebSocketSession> sessions = new HashSet<>(); // WebSocket 세션 관리

    public ChatRoomDTO(String id, String userId, String roomTitle, String passwd, LocalDateTime regDate, String region) {
        this.id = id;
        this.userId = userId;
        this.roomTitle = roomTitle;
        this.passwd = passwd;
        this.regDate = regDate;
        this.region = region;
    }

    // Getter 및 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<UserDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserDTO> participants) {
        this.participants = participants;
    }

    public boolean hasPassword() {
        return this.passwd != null && !this.passwd.isEmpty();
    }

    public void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeSession(WebSocketSession session) {
        sessions.remove(session);
    }

    public void handleMessage(WebSocketSession session, ChatMessage chatMessage, ObjectMapper objectMapper) throws JsonProcessingException {
        String userName  = chatMessage.getUserName();
        LocalDateTime regDate = chatMessage.getRegDate();
    	if ("JOIN".equals(chatMessage.getType())) {
            join(session);
        }
        send(chatMessage, objectMapper);
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
    // 해당 채팅방에 새 멤버 등록(세션 관리)
    private void join(WebSocketSession session) {
        sessions.add(session);
    }
    
 // 채팅방에 있는 모든 멤버에 메시지 전송
    private <T> void send(T messageObject, ObjectMapper objectMapper) throws JsonProcessingException {
        TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
  
        sessions.parallelStream().forEach(session -> {
            try {
                session.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // equals 및 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoomDTO that = (ChatRoomDTO) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // 포맷된 날짜 문자열 반환 메소드
    public String getFormattedRegDate() {
        if (this.regDate == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return this.regDate.format(formatter);
    }

    public boolean checkPasswd(String password) {
    	return this.passwd.equals(password);
    }
}
