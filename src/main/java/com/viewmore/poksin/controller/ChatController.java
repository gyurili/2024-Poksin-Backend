package com.viewmore.poksin.controller;

import com.viewmore.poksin.entity.ChatMessageEntity;
import com.viewmore.poksin.entity.ChatRoomEntity;
import com.viewmore.poksin.dto.user.UserResponseDTO;
import com.viewmore.poksin.service.ChatService;
import com.viewmore.poksin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.userService = userService;
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ChatRoomEntity createRoom() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }

        ChatRoomEntity existingRoom = chatService.findRoomByUserName(username);
        if (existingRoom != null) {
            return existingRoom;
        }

        return chatService.createRoom(username);
    }

    @GetMapping
    public List<ChatRoomEntity> findAllRoom() {
        return chatService.findAllRoom();
    }

    @GetMapping("/users")
    public List<UserResponseDTO> findAllUsers() {
        return userService.findAllUsers();
    }

    @MessageMapping("/chat.sendMessage")
    public ChatMessageEntity sendMessage(ChatMessageEntity chatMessage) {
        log.info("Received message to send: {}", chatMessage);

        if (chatMessage.getMessage() == null || chatMessage.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        if (chatMessage.getRoomId() == null || chatMessage.getRoomId().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be null or empty");
        }
        if (chatMessage.getSender() == null || chatMessage.getSender().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }
        if (chatMessage.getType() == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        chatService.sendChatMessage(chatMessage);

        ChatRoomEntity chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        if (chatRoom != null) {
            chatRoom.sendMessage(chatMessage, chatService);
        }
        return chatMessage;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(ChatMessageEntity chatMessage) {
        log.info("Received private message: {}", chatMessage);

        ChatRoomEntity chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        if (chatRoom != null) {
            chatRoom.sendMessage(chatMessage, chatService);
        }
    }

    @GetMapping("/rooms")
    public List<ChatRoomEntity> findAllChatRooms() {
        return chatService.findAllRoom();
    }

    @GetMapping("/rooms/messages")
    public List<ChatMessageEntity> getMessagesByRoomId(@RequestParam String roomId) {
        log.info("Fetching messages for roomId: {}", roomId);
        return chatService.findMessagesByRoomId(roomId);
    }
}
