package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime
import java.util.*

data class ChatMessage(
    val id: UUID,
    val from: UserId,
    val to: UserId,
    val content: String,
    val timestamp: LocalDateTime,
) {
    constructor(from: UserId, to: UserId, message: String) : this(
        id = UUID.randomUUID(),
        from = from,
        to = to,
        content = message,
        timestamp = LocalDateTime.now(),
    )
}

@JvmInline
value class UserId(val value: String)
