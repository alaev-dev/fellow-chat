package ru.alaev.fellowgigachat.chat.persistence.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "chatMessages")
data class ChatMessageEntity(
    @Id val id: UUID,
    val from: String,
    val to: String,
    val content: String,
    val timestamp: LocalDateTime
)