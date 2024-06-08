package ru.alaev.fellowgigachat.chat.dto.message

import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse
import ru.alaev.fellowgigachat.domain.ChatMessage

data class ChatMessageResponse(
    val id: String,
    val from: String,
    val to: String,
    val message: String,
    val timestamp: String,
) : ConvertibleToCommonResponse {

    companion object {
        fun from(chatMessage: ChatMessage): ChatMessageResponse {
            return ChatMessageResponse(
                id = chatMessage.id.toString(),
                from = chatMessage.sender.value,
                to = chatMessage.recipient.value,
                message = chatMessage.content,
                timestamp = chatMessage.timestamp.toString(),
            )
        }
    }
}