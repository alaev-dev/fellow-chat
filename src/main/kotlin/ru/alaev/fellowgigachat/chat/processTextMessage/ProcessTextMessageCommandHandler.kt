package ru.alaev.fellowgigachat.chat.processTextMessage

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.dto.ResponseType.MESSAGE
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageRequest
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory.SaveHistoryCommand
import ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory.SaveMessageCommandHandler
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.domain.Username

@Service
class ProcessTextMessageCommandHandler(
    private val saveMessageCommandHandler: SaveMessageCommandHandler,
    private val sessionManager: SessionManager,
) {
    fun handle(command: ProcessTextMessageCommand) {
        val message = command.chatMessage.toDomain(command.from)
        val response = ChatMessageResponse.from(message).toCommonResponse(MESSAGE)

        sessionManager.sendMessageToSession(message.recipient, response)

        if (message.recipient != message.sender) {
            sessionManager.sendMessageToSession(message.sender, response)
        }

        log.info("Message received: ${message.content} for ${message.recipient.value} from ${message.sender.value}")

        saveMessageCommandHandler.handle(SaveHistoryCommand(message))
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ProcessTextMessageCommand(
    val chatMessage: ChatMessageRequest,
    val from: Username,
)