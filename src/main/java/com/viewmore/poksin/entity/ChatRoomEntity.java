package com.viewmore.poksin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viewmore.poksin.service.ChatService;
import com.viewmore.poksin.service.SessionManager;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomEntity {
    private static final Logger logger = LoggerFactory.getLogger(ChatRoomEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;
    private String name;

    @JsonIgnore
    @Builder.Default
    private Set<String> sessionIds = new HashSet<>();

    @Setter
    private String lastMessage;

    @Setter
    @Builder.Default
    private LocalDateTime lastUpdated = LocalDateTime.now();

    public void handleActions(WebSocketSession session, ChatMessageEntity chatMessage, ChatService chatService) {
        if (chatMessage.getType().equals(ChatMessageEntity.MessageType.ENTER)) {
            sessionIds.add(session.getId());
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        setLastMessage(chatMessage.getMessage());
        setLastUpdated(LocalDateTime.now());
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessionIds.forEach(sessionId -> {
            WebSocketSession session = SessionManager.getSession(sessionId);
            if (session != null && session.isOpen()) {
                try {
                    chatService.sendMessage(session, message);
                } catch (Exception e) {
                    logger.error("메시지 전송 중 오류 발생: {}", e.getMessage(), e);
                }
            }
        });
    }

    public void removeSession(String sessionId) {
        sessionIds.remove(sessionId);
        SessionManager.removeSession(sessionId);
    }
}
