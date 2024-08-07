package com.finalproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import com.finalproject.dto.ChatRoomDTO;
import com.finalproject.dto.ChatRoomRepository;
import com.finalproject.dto.CreateChatRoomRequest;
import com.finalproject.dto.UserDTO;
import com.finalproject.dto.WebSocketHandler;

@RestController
@RequestMapping("/api/room")
public class ChatRoomController {

	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	  @Autowired
	    private WebSocketHandler webSocketHandler;
	
	 @GetMapping
	    public ResponseEntity<List<ChatRoomDTO>> getAllChatRooms() {
	        List<ChatRoomDTO> chatRooms = chatRoomRepository.listAllChatRooms();
	        return ResponseEntity.ok(chatRooms);
	    }

	@GetMapping("/{roomId}/participants")
	public ResponseEntity<List<UserDTO>> getParticipants(@PathVariable String roomId) {
		List<UserDTO> participants = chatRoomRepository.findParticipantsByRoomId(roomId);
		System.out.println("채팅기능확인");
		System.out.println(roomId);
		System.out.println(participants);
		return ResponseEntity.ok(participants);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createChatRoom(@RequestBody CreateChatRoomRequest request) {
		// 입력 파라미터 검증
		if (request.getRoomTitle() == null || request.getRoomTitle().trim().isEmpty() || request.getRegion() == null
				|| request.getRegion().trim().isEmpty() || request.getUserId() == null
				|| request.getUserId().trim().isEmpty()) {
			return ResponseEntity.badRequest().body("Invalid input parameters.");
		}

		// 채팅방 생성 로직
		String id = chatRoomRepository.createChatRoom(request.getRoomTitle(), request.getRegion(),
				request.getPasswd() != null ? request.getPasswd() : "", request.getUserId());

		return ResponseEntity.ok(id);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteChatRoom(@RequestParam("roomId") String roomId) {
		try {
			// Call the repository method and capture the result message
			String resultMessage = chatRoomRepository.deleteChatRoom(roomId);

			// Determine the HTTP status based on the result message
			if (resultMessage.contains("성공적으로 삭제되었습니다")) {
				return ResponseEntity.ok(resultMessage); // Return 200 OK for success
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMessage); // Return 404 Not Found for
																						// failure
			}
		} catch (Exception e) {
			// Log the exception if necessary
			e.printStackTrace(); // Optional: Log the stack trace for debugging
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("채팅방 삭제 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
	
	  @GetMapping("/{roomId}/members")
	    public ResponseEntity<List<UserDTO>> getChatRoomMembers(@PathVariable String roomId) {
	        try {
	            List<UserDTO> members = chatRoomRepository.getParticipants(roomId);
	            if (members.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	            }
	            return ResponseEntity.ok(members);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body(null);
	        }
	    }
	  
	  @PostMapping("/join")
	  public ResponseEntity<Map<String, String>> joinChatRoom(
	          @RequestParam String roomId,
	          @RequestParam(required = false) String password) {

	      // 여기에 비즈니스 로직을 추가합니다.
	      String result = chatRoomRepository.joinChatRoom(roomId, password);
	      Map<String, String> response = new HashMap<>();
	      response.put("message", result);
	      response.put("success", result.equals("채팅방에 성공적으로 입장했습니다.") ? "true" : "false");
	      
	      return ResponseEntity.ok(response);
	  }

}
