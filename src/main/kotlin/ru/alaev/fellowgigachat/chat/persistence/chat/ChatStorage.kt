package ru.alaev.fellowgigachat.chat.persistence.chat

import org.springframework.data.domain.Pageable
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.GroupId

interface ChatStorage {
    fun saveMessage(chatMessage: ChatMessage): ChatMessage
    fun getMessagesPageable(groupId: GroupId, page: Pageable): CollectPageableHistoryQueryResult
}

data class CollectPageableHistoryQueryResult(
    val pages: List<ChatMessage>,
    val total: Long,
)