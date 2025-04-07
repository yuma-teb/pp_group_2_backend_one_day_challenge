package com.practice.websocketbackend.controller;

import com.practice.websocketbackend.model.entity.ChatMessage;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/***
 * @MessageMapping is an annotation use route for websocket message
 *
 *
 */

@Controller
public class ChatController {


    private final HttpMessageConverters messageConverters;

    public ChatController(HttpMessageConverters messageConverters) {
        this.messageConverters = messageConverters;
    }

    // /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    /**
     *
     * this is the front end connection
     * websocket connection /topic/public
     */
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    // /app/chat.addUser
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setContent(chatMessage.getSender() + " joined the chat");
        return chatMessage;
    }

    @MessageMapping("/chat.send.{roomId}")
    public ChatMessage sendToRoom(@DestinationVariable String roomId, ChatMessage chatMessage) {
        return chatMessage;
    };
}
