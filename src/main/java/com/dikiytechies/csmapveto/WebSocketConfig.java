package com.dikiytechies.csmapveto;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final RoomWebSocketHandler roomHandler;

    public WebSocketConfig(RoomWebSocketHandler roomHandler) {
        this.roomHandler = roomHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(roomHandler, "/room/*/");
    }
}
