package com.viewmore.poksin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewmore.poksin.entity.ChatMessageEntity;
import com.viewmore.poksin.entity.ChatRoomEntity;
import com.viewmore.poksin.repository.ChatMessageRepository;
import com.viewmore.poksin.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ObjectMapper objectMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatRoomEntity> findAllRoom() {
        return chatRoomRepository.findAll();
    }

    public ChatRoomEntity findRoomById(String roomId) {
        return chatRoomRepository.findByRoomId(roomId).orElse(null);
    }

    public ChatRoomEntity findRoomByUserName(String userName) {
        return chatRoomRepository.findByName(userName).orElse(null);
    }

    public ChatRoomEntity createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoomEntity chatRoom = ChatRoomEntity.builder()
                .roomId(randomId)
                .name(name)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            } catch (IOException e) {
                log.error("Error sending message: {}", e.getMessage(), e);
            }
        } else {
            log.warn("Session is null or closed, cannot send message.");
        }
    }

    public void removeSession(String roomId, String sessionId) {
        ChatRoomEntity chatRoom = findRoomById(roomId);
        if (chatRoom != null) {
            chatRoom.removeSession(sessionId);
        }
    }

    @Transactional
    public ChatMessageEntity sendChatMessage(ChatMessageEntity message) {
        log.info("Sending chat message: {}", message);

        message.setTimestamp(LocalDateTime.now());
        ChatMessageEntity savedMessage = chatMessageRepository.save(message);

        ChatRoomEntity chatRoom = chatRoomRepository.findByRoomId(message.getRoomId())
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
        chatRoom.setLastMessage(message.getMessage());
        chatRoom.setLastUpdated(LocalDateTime.now());
        chatRoomRepository.save(chatRoom);

        return savedMessage;
    }

    public List<ChatMessageEntity> findMessagesByRoomId(String roomId) {
        log.info("Finding messages for roomId: {}", roomId);
        return chatMessageRepository.findByRoomId(roomId);
    }


}
