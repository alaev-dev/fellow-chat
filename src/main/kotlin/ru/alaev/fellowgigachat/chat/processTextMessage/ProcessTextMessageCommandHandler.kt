package ru.alaev.fellowgigachat.chat.processTextMessage

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.dto.ChatMessageRequest
import ru.alaev.fellowgigachat.chat.dto.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory.SaveHistoryCommand
import ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory.SaveHistoryCommandHandler
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId
import java.util.concurrent.ConcurrentHashMap

@Service
class ProcessTextMessageCommandHandler(
    private val saveHistoryCommandHandler: SaveHistoryCommandHandler,
    private val objectMapper: ObjectMapper,
) {
    fun handle(command: ProcessTextMessageCommand) {
        val message = command.chatMessage.toDomain(command.from)

        command.sessions[message.to]?.sendMessage(
            TextMessage(
                ChatMessageResponse.from(message).toJson(objectMapper)
            )
        )

        log.info("Message received: ${message.content} for ${message.to.value} from ${message.from.value}")

        saveHistoryCommandHandler.handle(SaveHistoryCommand(message, command.chatHistory))
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ProcessTextMessageCommand(
    val chatMessage: ChatMessageRequest,
    val from: UserId,
    val sessions: ConcurrentHashMap<UserId, WebSocketSession>,
    val chatHistory: ConcurrentHashMap<UserId, MutableList<ChatMessage>>
)