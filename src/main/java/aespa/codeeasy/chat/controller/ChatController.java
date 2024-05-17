package aespa.codeeasy.chat.controller;

import aespa.codeeasy.chat.domain.ChatMessage;
import aespa.codeeasy.chat.pubsub.RedisPublisher;
import aespa.codeeasy.chat.repository.ChatMessageRepository;
import aespa.codeeasy.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
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
    public List<ChatMessage> searchMessages(@RequestParam String keyword) {
        return chatMessageRepository.findByMessageContaining(keyword);
    }

    @GetMapping("/chats/messages/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return chatMessageRepository.findByRoomId(roomId);
    }
}