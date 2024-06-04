package ru.alaev.fellowgigachat.chat.persistence.chat.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "chatMessages")
data class ChatMessageEntity(
    @Id val id: UUID,
    val from: UUID,
    val to: UUID,
    val content: String,
    val timestamp: LocalDateTime
)