package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime
import java.util.UUID

class ChatMessage private constructor(
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatMessage

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}

@JvmInline
value class UserId(val value: String)
