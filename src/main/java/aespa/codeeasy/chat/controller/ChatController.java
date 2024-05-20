package aespa.codeeasy.chat.controller;

import aespa.codeeasy.chat.domain.ChatMessage;
import aespa.codeeasy.chat.pubsub.RedisPublisher;
import aespa.codeeasy.chat.repository.ChatMessageRepository;
import aespa.codeeasy.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api") // API 경로 설정
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chats/send")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterMainRoom();
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        chatMessageRepository.save(message); // 메시지 저장
        redisPublisher.publish(chatRoomRepository.getMainRoomTopic(), message);
    }

    @GetMapping("/chats/search") // 추가: 검색 엔드포인트
    @ResponseBody
    public List<ChatMessage> searchMessages(@RequestParam(name = "keyword") String keyword) {
        try {
            return chatMessageRepository.findByMessageContaining(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error searching messages", e);
        }
    }

    @GetMapping("/chats/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable(name = "roomId") String roomId) {
        try {
            List<ChatMessage> messages = chatMessageRepository.findByRoomId(roomId);
            System.out.println("Messages fetched successfully for room: " + roomId);
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching messages", e);
        }
    }
}