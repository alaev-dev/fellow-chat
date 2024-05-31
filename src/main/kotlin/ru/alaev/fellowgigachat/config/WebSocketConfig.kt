package ru.alaev.fellowgigachat.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import ru.alaev.fellowgigachat.chat.ChatHandler

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val chatHandler: ChatHandler
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*")
    }
}