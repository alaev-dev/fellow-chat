package ru.alaev.fellowgigachat.chat.message.handler

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.ChatMessage

@Service
class ReadMessageCommandHandler(
    private val chatStorage: ChatStorage,
) {
    @Transactional
    fun handle(command: ReadMessageCommand): ChatMessage {
        val message = chatStorage.getMessageByID(command.messageID)
            ?: throw DomainException("Message ${command.messageID} not found", NOT_FOUND)
        message.isRead = true
        return message.toDomain()
    }
}

data class ReadMessageCommand(
    val messageID: Long
)