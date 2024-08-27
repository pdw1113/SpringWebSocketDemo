package com.matt.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * [WebSocketMessageBrokerConfigurer]
 * WebSocket 연결,
 * 메시지 브로커,
 * STOMP 엔드포인트 설정
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	/**
	 *  클라이언트가 WebSocket 서버에 연결할 때 사용할 STOMP 엔드포인트(URL : /ws)를 등록
	 *  클라이언트는 이 엔드포인트(/ws)를 통해 WebSocket 연결을 시작하게 됩니다. 
	 *  SockJS 폴백 옵션도 여기서 설정할 수 있습니다.
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/ws").withSockJS();
	}
	
	/**
	 *	메시지 브로커를 설정하여, 메시지가 어디로 라우팅될지 결정
	 *	예를 들어, 
	 *	/topic과 같은 주제를 기반으로 메시지를 브로드캐스팅하거나, 
	 *	/queue와 같은 큐에 메시지를 보낼 수 있도록 설정합니다.
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
		// 클라이언트에서 보낸 메시지가 /app으로 시작하면, 메시지가 ChatController의 @MessageMapping의 메서드로 라우팅 된다.
		registry.setApplicationDestinationPrefixes("/app"); // 애플리케이션 목적지의 기본 접두사 설정
		// /topic/으로 시작하는 url로 메세지를 보낼 수 있게 해준다.
		registry.enableSimpleBroker("/topic"); 
	}

}
