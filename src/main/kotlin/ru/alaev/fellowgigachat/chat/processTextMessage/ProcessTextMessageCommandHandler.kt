package ru.alaev.fellowgigachat.chat.processTextMessage

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.dto.ResponseType.MESSAGE
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.dto.message.WebsocketChatMessageRequest
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

        saveMessageCommandHandler.handle(SaveHistoryCommand(message))

        message.group.members.toSet().forEach {
            sessionManager.sendMessageToSession(
                username = it,
                message = ChatMessageResponse.from(message).toCommonResponse(MESSAGE)
            )
        }

        log.info("Message received: ${message.content} for ${message.group} from ${message.sender}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ProcessTextMessageCommand(
    val chatMessage: WebsocketChatMessageRequest,
    val from: Username,
)