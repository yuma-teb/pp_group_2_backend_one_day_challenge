package com.practice.websocketbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        /**
         * this is the in memory message broker,
         * broker handle the message with every connetion that start with the prefix /topic
         */
        config.enableSimpleBroker("/topic");

        /**
         * this is the websocket route
         * used to define the controller which will handle the websocker connection
         * example: /app/chat.message
         */
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * - addEndpoint("/ws")
     *      -the websocket connection only available on the /ws of backend endpoint
     * - setAllowedOriginPatterns("*")
     *      - allow to connection from everywhere
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}