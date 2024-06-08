package ru.alaev.fellowgigachat.chat.persistence.chat.model

import ru.alaev.fellowgigachat.domain.ChatMessage

data class CollectPageableHistoryQueryResult(
    val pages: List<ChatMessage>,
    val total: Long,
)