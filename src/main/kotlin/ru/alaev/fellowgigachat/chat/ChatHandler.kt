package ru.alaev.fellowgigachat.chat

import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

@Component
class ChatHandler : TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val userIds = ConcurrentHashMap<WebSocketSession, String>()
    private val chatHistory = ConcurrentHashMap<String, MutableList<ChatMessage>>()
    private val objectMapper = jacksonObjectMapper()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = session.uri?.query?.split("=")?.get(1) ?: session.id
        sessions[userId] = session
        userIds[session] = userId

        log.info("New session established: $userId")

        // Send chat history to the connected user
        chatHistory[userId]?.let { history ->
            history.forEach { message ->
                session.sendMessage(TextMessage(objectMapper.writeValueAsString(message)))
            }
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val chatMessage: Message = objectMapper.readValue(payload)
        val fromUserId = userIds[session] ?: "unknown"

        val timestamp = LocalDateTime.now()
        val fullMessage = ChatMessage(
            from = fromUserId,
            to = chatMessage.to,
            message = chatMessage.message,
            timestamp = timestamp.toString()
        )

        log.info("Message received: ${chatMessage.message} for ${chatMessage.to} from $fromUserId")

        // Store the message in history
        chatHistory.computeIfAbsent(chatMessage.to) { mutableListOf() }.add(fullMessage)
        chatHistory.computeIfAbsent(fromUserId) { mutableListOf() }.add(fullMessage)

        sessions[chatMessage.to]?.sendMessage(TextMessage(objectMapper.writeValueAsString(fullMessage)))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userId = userIds[session]
        if (userId != null) {
            sessions.remove(userId)
            userIds.remove(session)
            log.info("Session closed: $userId")
        } else {
            log.info("Session closed: ${session.id}")
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ChatMessage(
    val from: String,
    val to: String,
    val message: String,
    val timestamp: String
)
