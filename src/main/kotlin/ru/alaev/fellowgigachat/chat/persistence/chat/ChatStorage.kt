package ru.alaev.fellowgigachat.chat.persistence.chat

import org.springframework.data.domain.Pageable
import ru.alaev.fellowgigachat.chat.processTextMessage.saveHistory.MessageId
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.GroupName

interface ChatStorage {
    fun saveMessage(chatMessage: ChatMessage): MessageId
    fun getMessagesPageable(groupName: GroupName, page: Pageable): CollectPageableHistoryQueryResult
}

data class CollectPageableHistoryQueryResult(
    val pages: List<ChatMessage>,
    val total: Long,
)