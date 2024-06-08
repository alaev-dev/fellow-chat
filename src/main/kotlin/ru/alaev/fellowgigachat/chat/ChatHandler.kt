package ru.alaev.fellowgigachat.chat

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageRequest
import ru.alaev.fellowgigachat.chat.processConnection.ConnectionProcessCommand
import ru.alaev.fellowgigachat.chat.processConnection.ConnectionProcessCommandHandler
import ru.alaev.fellowgigachat.chat.processTextMessage.ProcessTextMessageCommand
import ru.alaev.fellowgigachat.chat.processTextMessage.ProcessTextMessageCommandHandler
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.domain.Username

@Component
class ChatHandler(
    private val connectionProcess: ConnectionProcessCommandHandler,
    private val processTextMessageCommandHandler: ProcessTextMessageCommandHandler,
    private val objectMapper: ObjectMapper,
    private val sessionManager: SessionManager,
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val userId = getUsername(session)

        connectionProcess.handle(ConnectionProcessCommand(session, userId))
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val username = getUsername(session)
        val chatMessage: ChatMessageRequest = objectMapper.readValue(message.payload)

        processTextMessageCommandHandler.handle(ProcessTextMessageCommand(chatMessage, username))
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val username = getUsername(session)
        sessionManager.removeConnection(username)
        log.info("Session closed for: ${username.value} with status :: ${status.reason} and code :: ${status.code}")
    }

    private fun getUsername(session: WebSocketSession): Username {
        return Username(session.uri?.query?.split("=")?.get(1) ?: session.id)
    }


    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}
