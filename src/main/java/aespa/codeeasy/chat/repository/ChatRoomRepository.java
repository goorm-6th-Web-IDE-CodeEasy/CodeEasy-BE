package aespa.codeeasy.chat.repository;

import aespa.codeeasy.chat.domain.ChatRoom;
import aespa.codeeasy.chat.pubsub.RedisSubscriber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;
    private static final String MAIN_ROOM_ID = "main-room";

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
        createMainRoom();
    }

    private void createMainRoom() {
        if (opsHashChatRoom.get(CHAT_ROOMS, MAIN_ROOM_ID) == null) {
            ChatRoom mainRoom = ChatRoom.create("CodeEasy Chatting Room");
            mainRoom.setRoomId(MAIN_ROOM_ID);
            opsHashChatRoom.put(CHAT_ROOMS, MAIN_ROOM_ID, mainRoom);
        }
    }

    public ChatRoom getMainRoom() {
        return opsHashChatRoom.get(CHAT_ROOMS, MAIN_ROOM_ID);
    }

    public void enterMainRoom() {
        ChannelTopic topic = topics.get(MAIN_ROOM_ID);
        if (topic == null) {
            topic = new ChannelTopic(MAIN_ROOM_ID);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(MAIN_ROOM_ID, topic);
        }
    }

    public ChannelTopic getMainRoomTopic() {
        return topics.get(MAIN_ROOM_ID);
    }

    public ChatRoom findRoomById(String roomId) {
        return opsHashChatRoom.get(CHAT_ROOMS, roomId);
    }

    public ChatRoom createChatRoom(String name) {
        String roomId = name + "-" + System.currentTimeMillis();
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoom.setRoomId(roomId);
        opsHashChatRoom.put(CHAT_ROOMS, roomId, chatRoom);
        return chatRoom;
    }

    public List<ChatRoom> findAllRooms() {
        return opsHashChatRoom.values(CHAT_ROOMS);
    }

    public void subscribeToRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

}