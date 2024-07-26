package com.viewmore.poksin.dto.chat;

import com.viewmore.poksin.entity.ChatRoomEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomDTO {
    private String roomId;
    private String roomName;
    private String lastMessage;
    private LocalDateTime lastUpdated;

    public ChatRoomDTO(ChatRoomEntity chatRoomEntity) {
        this.roomId = chatRoomEntity.getRoomId();
        this.roomName = chatRoomEntity.getName();
        this.lastMessage = chatRoomEntity.getLastMessage();
        this.lastUpdated = chatRoomEntity.getLastUpdated();
    }
}
