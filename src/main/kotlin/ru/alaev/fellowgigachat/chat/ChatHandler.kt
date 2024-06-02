package ru.alaev.fellowgigachat.chat

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.alaev.fellowgigachat.chat.dto.ChatMessageRequest
import ru.alaev.fellowgigachat.chat.processConnection.ConnectionProcessCommand
import ru.alaev.fellowgigachat.chat.processConnection.ConnectionProcessCommandHandler
import ru.alaev.fellowgigachat.chat.processTextMessage.ProcessTextMessageCommand
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.chat.processTextMessage.ProcessTextMessageCommandHandler
import ru.alaev.fellowgigachat.domain.UserId
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatHandler(
    private val connectionProcess: ConnectionProcessCommandHandler,
    private val processTextMessageCommandHandler: ProcessTextMessageCommandHandler,
    private val objectMapper: ObjectMapper,
) : TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap<UserId, WebSocketSession>()
    private val chatHistory = ConcurrentHashMap<UserId, MutableList<ChatMessage>>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = getUserId(session)

        connectionProcess.handle(ConnectionProcessCommand(session, userId, sessions, chatHistory))
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val userId = getUserId(session)
        val chatMessage: ChatMessageRequest = objectMapper.readValue(message.payload)

        processTextMessageCommandHandler.handle(ProcessTextMessageCommand(chatMessage, userId, sessions, chatHistory))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val userId = getUserId(session)
        sessions.remove(userId)
        log.info("Session closed for: ${userId.value} with status :: ${status.reason} and code :: ${status.code}")
    }

    private fun getUserId(session: WebSocketSession): UserId {
        return UserId(session.uri?.query?.split("=")?.get(1) ?: session.id)
    }


    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
