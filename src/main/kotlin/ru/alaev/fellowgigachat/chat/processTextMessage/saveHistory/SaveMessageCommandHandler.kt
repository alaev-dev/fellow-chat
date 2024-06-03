package ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.ChatStorage
import ru.alaev.fellowgigachat.domain.ChatMessage

@Service
class SaveMessageCommandHandler(
    private val storage: ChatStorage,
) {
    fun handle(command: SaveHistoryCommand) {
        storage.saveMessage(command.content)

        log.info("Save message for: ${command.content.from.value}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class SaveHistoryCommand(
    val content: ChatMessage,
)