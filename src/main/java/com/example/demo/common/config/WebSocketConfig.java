package com.example.demo.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import com.example.demo.common.handler.ChatHandler;
import com.example.demo.common.interceptor.HttpSessionHandshakeInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//	private final ChatHandler chatHandler;
//	private final StompHandler stompHandler;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/sub");
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/chat").setAllowedOriginPatterns("*").addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
		registry.addEndpoint("/stomp/channel").setAllowedOriginPatterns("*").withSockJS();
		registry.addEndpoint("/stomp/signaling").setAllowedOriginPatterns("*").withSockJS();
		registry.addEndpoint("/stomp/dm").setAllowedOriginPatterns("*").withSockJS();

	}
	
	@Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
		registry.setMessageSizeLimit(160*64*1024);
		
	}
	

//	@Override
//	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//		registry.addHandler(chatHandler, "ws/chat/").setAllowedOrigins("*");
//	}
	
	
}
