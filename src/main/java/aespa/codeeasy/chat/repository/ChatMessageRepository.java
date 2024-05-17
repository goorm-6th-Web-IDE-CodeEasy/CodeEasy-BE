package aespa.codeeasy.chat.repository;

import aespa.codeeasy.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomId(String roomId);
    List<ChatMessage> findByMessageContaining(String keyword); // 추가: 메시지 검색 메서드
}
