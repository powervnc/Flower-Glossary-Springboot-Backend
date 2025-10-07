package com.example.server_flowers.config;

import com.example.server_flowers.PlantWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final PlantWebSocketHandler plantWebSocketHandler;

    public WebSocketConfig(PlantWebSocketHandler plantWebSocketHandler) {
        this.plantWebSocketHandler = plantWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(plantWebSocketHandler, "/ws/plants")
                .setAllowedOrigins("*");
    }
}
