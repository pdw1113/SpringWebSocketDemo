package com.matt.chat.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.matt.chat.chat.ChatMessage;
import com.matt.chat.chat.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messageTemplate;

	// 유저가 채팅방을 떠났을 때,
	@EventListener
	public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
		
		StompHeaderAccessor headerAccesor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String)headerAccesor.getSessionAttributes().get("username");
		if(username != null) {
			log.info("User Disconnected: {}", username);
			ChatMessage chatMessage = ChatMessage.builder()
										.type(MessageType.LEAVE)
										.sender(username)
										.build();
			messageTemplate.convertAndSend("/topic/public", chatMessage);
		}
	}
	
	
}
