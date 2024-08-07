package com.finalproject.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger; // AtomicInteger를 위한 임포트 추가

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class ChatRoomRepository {

    private final Map<String, ChatRoomDTO> chatRoomMap = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(0); // 자동 증가하는 ID 카운터

    // 채팅방 가져오기 (roomId를 통해)
    public ChatRoomDTO getChatRoom(String roomId) {
        return chatRoomMap.get(roomId);
    }

    // 모든 채팅방 가져오기
    public Collection<ChatRoomDTO> getChatRooms() {
        return chatRoomMap.values();
    }

    // 채팅방 생성
    public String createChatRoom(String roomTitle, String region, String passwd, String userId) {
        int newId = idCounter.incrementAndGet(); // 자동 증가하는 ID 생성
        String roomId = String.valueOf(newId); // ID를 문자열로 변환
        ChatRoomDTO newRoom = new ChatRoomDTO(roomId, userId, roomTitle, passwd, LocalDateTime.now(), region);
        chatRoomMap.put(roomId, newRoom); // ID를 키로 사용하여 저장
        return roomId; // ID 반환
    }

    // 채팅방 목록 조회
    public List<ChatRoomDTO> listAllChatRooms() {
        return List.copyOf(chatRoomMap.values());
    }

    // 특정 채팅방의 참여자 목록 조회
    public List<UserDTO> getParticipants(String roomId) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        if (room != null) {
            return room.getParticipants();
        }
        return Collections.emptyList();
    }

    // 채팅방 삭제
    public String deleteChatRoom(String roomId) {
        if (chatRoomMap.containsKey(roomId)) {
            chatRoomMap.remove(roomId);
            return "채팅방이 성공적으로 삭제되었습니다.";
        }
        return "채팅방 삭제에 실패했습니다: 채팅방을 찾을 수 없습니다.";
    }

    // 채팅방 업데이트
    public boolean updateChatRoom(String roomId, String newTitle, String newRegion) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        if (room != null) {
            room.setRoomTitle(newTitle);
            room.setRegion(newRegion);
            return true;
        }
        return false;
    }

    // 특정 채팅방의 참여자 목록 조회 (roomId를 통해)
    public List<UserDTO> findParticipantsByRoomId(String roomId) {
        ChatRoomDTO room = chatRoomMap.get(roomId);
        if (room != null) {
            return room.getParticipants();
        }
        return List.of(); // 빈 리스트 반환
    }

    public void remove(WebSocketSession session) {
        for (ChatRoomDTO room : chatRoomMap.values()) {
            room.removeSession(session);
        }
    }

    public String joinChatRoom(String roomId, String password) {
        // 채팅방 존재 여부 확인
        ChatRoomDTO room = chatRoomMap.get(roomId);

        if (room == null) {
            return "채팅방이 존재하지 않습니다.";
        }

        // 비밀번호 확인
        if (room.getPasswd() != null && !room.checkPasswd(password)) {
            return "비밀번호가 틀렸습니다.";
        }

        return "채팅방에 성공적으로 입장했습니다.";
    }

}
