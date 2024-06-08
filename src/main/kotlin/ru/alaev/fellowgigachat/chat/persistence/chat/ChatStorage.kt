package ru.alaev.fellowgigachat.chat.persistence.chat

import org.springframework.data.domain.Pageable
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Username

interface ChatStorage {
    fun saveMessage(chatMessage: ChatMessage)
    fun getMessagesPageable(username: Username, page: Pageable): CollectPageableHistoryQueryResult
}

data class CollectPageableHistoryQueryResult(
    val pages: List<ChatMessage>,
    val total: Long,
)