package ru.alaev.fellowgigachat.chat.dto

import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId

data class ChatMessageRequest(
    var to: String,
    var message: String,
) {
    fun toDomain(from: UserId): ChatMessage {
        return ChatMessage(
            from = from,
            to = UserId(to),
            message = message,
        )
    }
}
