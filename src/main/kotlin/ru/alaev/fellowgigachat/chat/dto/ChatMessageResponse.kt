package ru.alaev.fellowgigachat.chat.dto

import com.fasterxml.jackson.databind.ObjectMapper
import ru.alaev.fellowgigachat.domain.ChatMessage

data class ChatMessageResponse(
    val id: String,
    val from: String,
    val to: String,
    val message: String,
    val timestamp: String,
) {
    fun toJson(objectMapper: ObjectMapper): String {
        return objectMapper.writeValueAsString(this)
    }

    companion object {
        fun from(chatMessage: ChatMessage): ChatMessageResponse {
            return ChatMessageResponse(
                id = chatMessage.id.toString(),
                from = chatMessage.from.value,
                to = chatMessage.to.value,
                message = chatMessage.content,
                timestamp = chatMessage.timestamp.toString(),
            )
        }
    }
}
