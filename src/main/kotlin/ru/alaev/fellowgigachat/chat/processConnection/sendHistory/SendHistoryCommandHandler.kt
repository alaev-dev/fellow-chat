package ru.alaev.fellowgigachat.chat.processConnection.sendHistory

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.dto.ChatMessageResponse
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId
import java.util.concurrent.ConcurrentHashMap

@Service
class SendHistoryCommandHandler(
    private val objectMapper: ObjectMapper,
) {
    // Send chat history to the connected user
    fun handle(command: SendHistoryCommand) {
        val userHistory = command.chatHistory[command.userId]

        userHistory?.let { history ->
            history.forEach { message ->
                command.session.sendMessage(TextMessage(ChatMessageResponse.from(message).toJson(objectMapper)))
            }
        }

        log.info(
            "Send history for: ${command.userId.value}, sends " +
                    "${if (userHistory.isNullOrEmpty()) "0" else userHistory.count()} messages"
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class SendHistoryCommand(
    val session: WebSocketSession,
    val userId: UserId,
    val chatHistory: ConcurrentHashMap<UserId, MutableList<ChatMessage>>
)