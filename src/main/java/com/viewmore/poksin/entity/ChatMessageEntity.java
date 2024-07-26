package com.viewmore.poksin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;

    public enum MessageType {
        ENTER, TALK, PRIVATE
    }
}
