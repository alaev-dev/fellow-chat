package ru.alaev.fellowgigachat.chat

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@Component
class ChatHandler : TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val userIds = ConcurrentHashMap<WebSocketSession, String>()
    private val objectMapper = jacksonObjectMapper()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = session.uri?.query?.split("=")?.get(1) ?: session.id
        sessions[userId] = session
        userIds[session] = userId
        println("New session established: $userId")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val chatMessage: Message = objectMapper.readValue(payload)
        val fromUserId = userIds[session] ?: "unknown"

        println("Message received: ${chatMessage.message} for ${chatMessage.to} from $fromUserId")
        sessions[chatMessage.to]?.sendMessage(
            TextMessage(objectMapper.writeValueAsString(mapOf(
                "from" to fromUserId,
                "message" to chatMessage.message
            )))
        )
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userId = userIds[session]
        if (userId != null) {
            sessions.remove(userId)
            userIds.remove(session)
            println("Session closed: $userId")
        } else {
            println("Session closed: ${session.id}")
        }
    }
}
