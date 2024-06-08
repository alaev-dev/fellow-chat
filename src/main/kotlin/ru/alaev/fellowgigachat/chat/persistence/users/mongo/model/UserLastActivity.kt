package ru.alaev.fellowgigachat.chat.persistence.users.mongo.model

import java.time.LocalDateTime

data class UserLastActivity(
    val username: String,
    val status: String,
    val lastMessage: LastChatMessage,
)

data class LastChatMessage(
    val id: String,
    val content: String,
    val timestamp: LocalDateTime,
)
