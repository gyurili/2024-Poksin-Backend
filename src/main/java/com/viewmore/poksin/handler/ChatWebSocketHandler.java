package com.viewmore.poksin.handler;

import com.viewmore.poksin.entity.ChatMessageEntity;
import com.viewmore.poksin.service.ChatService;
import com.viewmore.poksin.service.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class ChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        SessionManager.addSession(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessageEntity chatMessage = new ChatMessageEntity();
        chatMessage.setType(ChatMessageEntity.MessageType.TALK); // 메시지 타입
        chatMessage.setRoomId("roomId");
        chatMessage.setSender("senderId");
        chatMessage.setMessage(message.getPayload()); // 받은 메시지

        // 메시지 => 데이터베이스에 저장
        chatService.sendChatMessage(chatMessage);

        chatService.sendMessage(session, chatMessage);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        SessionManager.removeSession(session.getId());
    }
}
