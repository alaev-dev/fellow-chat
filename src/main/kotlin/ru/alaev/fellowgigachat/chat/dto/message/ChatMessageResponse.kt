package ru.alaev.fellowgigachat.chat.dto.message

import io.swagger.v3.oas.annotations.media.Schema
import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse
import ru.alaev.fellowgigachat.domain.ChatMessage

@Schema(description = "Response message for chat communication")
data class ChatMessageResponse(
    @Schema(description = "Message ID")
    val id: String,
    @Schema(description = "Sender username")
    val from: String,
    @Schema(description = "Recipient username")
    val to: String,
    @Schema(description = "Message content")
    val message: String,
    @Schema(description = "Timestamp of the message")
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
