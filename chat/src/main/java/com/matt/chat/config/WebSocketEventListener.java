package com.matt.chat.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

	public void handleWebSocketDisConnectListener(SessionDisconnectEvent event) {
		// TODO -- to be implemented
	}
	
	
}
