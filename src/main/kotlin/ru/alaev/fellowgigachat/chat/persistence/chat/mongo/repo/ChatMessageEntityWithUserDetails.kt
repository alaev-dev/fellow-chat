package ru.alaev.fellowgigachat.chat.persistence.chat.mongo.repo

import ru.alaev.fellowgigachat.chat.persistence.users.mongo.model.UserEntity
import java.time.LocalDateTime
import java.util.*

data class ChatMessageEntityWithUserDetails(
    val id: UUID,
    val from: UUID,
    val to: UUID,
    val content: String,
    val timestamp: LocalDateTime,
    val fromUser: UserEntity,
    val toUser: UserEntity
)

