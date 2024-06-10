package ru.alaev.fellowgigachat.chat.persistence.chat.postgres.repo

import java.time.LocalDateTime
import java.util.*
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity

data class ChatMessageEntityWithUserDetails(
    val id: UUID,
    val from: UUID,
    val to: UUID,
    val content: String,
    val timestamp: LocalDateTime,
    val fromUser: UserEntity,
    val toUser: UserEntity
)

