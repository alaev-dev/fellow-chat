package ru.alaev.fellowgigachat.chat.processConnection.sendHistory

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.dto.ResponseType.MESSAGE
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.domain.Username

@Service
class SendHistoryCommandHandler(
    private val storage: ChatStorage,
    private val objectMapper: ObjectMapper,
) {
    // Send chat history to the connected user
    fun handle(command: SendHistoryCommand) {
        val userHistory = storage.getLatestMessages(command.username)

        userHistory.forEach { message ->
            command.session.sendMessage(
                TextMessage(
                    ChatMessageResponse.from(message).toCommonResponse(MESSAGE).toJson(objectMapper)
                )
            )
        }

        log.info("Send history for: ${command.username.value}, sends ${userHistory.count()} messages")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class SendHistoryCommand(
    val session: WebSocketSession,
    val username: Username,
)