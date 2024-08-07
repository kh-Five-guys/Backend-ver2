package com.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.finalproject.dto.ChatRoomRepository;

@Controller
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;

    public ChatController(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    // 채팅방 목록을 보여줍니다
    @GetMapping("/chat")
    public ModelAndView main(ModelAndView view) {
        view.addObject("collection", chatRoomRepository.getChatRooms());
        view.setViewName("index");
        return view;
    }

    // 특정 채팅방을 ID로 보여줍니다
    @GetMapping("/chat/room/{id}")
    public ModelAndView roomController(@PathVariable("id") String id, ModelAndView view) {
        view.setViewName("room");
        view.addObject("roomId", id);
        return view;
    }

    // 새 채팅방 생성 페이지를 보여줍니다
    @CrossOrigin
    @GetMapping("/chat/room/new")
    public ModelAndView newRoomPage(ModelAndView view) {
        view.setViewName("newRoom");
        return view;
    }

    // 채팅방 생성 API로 리다이렉트합니다
    @CrossOrigin
    @GetMapping("/chat/room/create")
    public String newRoom() {
        return "redirect:/api/room/create";
    }
}
