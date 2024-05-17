package aespa.codeeasy.chat.controller;

import aespa.codeeasy.chat.domain.ChatRoom;
import aespa.codeeasy.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/chats")
    public String mainRoom(Model model) {
        ChatRoom mainRoom = chatRoomRepository.getMainRoom();
        chatRoomRepository.enterMainRoom();
        model.addAttribute("roomId", mainRoom.getRoomId());
        model.addAttribute("roomName", mainRoom.getName());
        return "room";
    }
    @GetMapping("/chats/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}