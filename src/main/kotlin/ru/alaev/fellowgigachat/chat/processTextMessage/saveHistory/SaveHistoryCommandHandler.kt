package ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId
import java.util.concurrent.ConcurrentHashMap

@Service
class SaveHistoryCommandHandler {
    fun handle(command: SaveHistoryCommand) {
        command.chatHistory.computeIfAbsent(command.content.from) { mutableListOf() }.add(command.content)
        command.chatHistory.computeIfAbsent(command.content.to) { mutableListOf() }.add(command.content)

        log.info("Save message to history for: ${command.content.from.value}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class SaveHistoryCommand(
    val content: ChatMessage,
    val chatHistory: ConcurrentHashMap<UserId, MutableList<ChatMessage>>
)