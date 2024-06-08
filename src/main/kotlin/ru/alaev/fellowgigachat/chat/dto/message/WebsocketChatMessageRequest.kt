package ru.alaev.fellowgigachat.chat.dto.message

import io.swagger.v3.oas.annotations.media.Schema
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Username

@Schema(description = "Request message for websocket chat communication")
data class WebsocketChatMessageRequest(
    @Schema(description = "Recipient username")
    var to: String,
    @Schema(description = "Message content")
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
