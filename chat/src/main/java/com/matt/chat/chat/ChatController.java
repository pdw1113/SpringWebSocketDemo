package com.matt.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.matt.chat.config.WebSocketEventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 채팅 관련 Controller
 * 
 * 
 */
@Controller
@Slf4j
public class ChatController {

	/**
	 * ChatMessage 객체
	 * content 	: 채팅 내용 
	 * sender 	: 채팅 보낸 사람
	 * type 	: 입장, 퇴장, 메세지 3가지로 분류
	 * 
	 * 1. 유저가 입장 할 때, JOIN
	 * 2. 유저가 퇴장 할 때, LEAVE
	 * 3. 유저가 채팅 할 때, CHAT
	 * 
	 * /toplic/public을 [subscribe] 하고있는 유저들에게 메세지를 보내준다.
	 * 
	 * @param chatMessage
	 * @return
	 */
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		log.info("Message Sent: {} >> {}", chatMessage.getSender(), chatMessage.getContent());
		return chatMessage;
	}

	/**
	 * @param chatMessage
	 * @param headerAccessor
	 * @return
	 */
	@MessageMapping("/chat.addUser")
	@SendTo("/toplic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		log.info("User Connected: {}", chatMessage.getSender());

		// 웹소켓 세션에 유저이름을 추가한다.
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}
