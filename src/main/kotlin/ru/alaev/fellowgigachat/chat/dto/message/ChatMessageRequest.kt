package ru.alaev.fellowgigachat.chat.dto.message

import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Username

data class ChatMessageRequest(
    var to: String,
    var message: String,
) {
    fun toDomain(from: Username): ChatMessage {
        return ChatMessage(
            from = from,
            to = Username(to),
            message = message,
        )
    }
}
