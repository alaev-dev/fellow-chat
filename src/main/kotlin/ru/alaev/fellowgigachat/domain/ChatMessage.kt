package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime
import java.util.*

data class ChatMessage(
    val id: UUID,
    val from: Username,
    val to: Username,
    val content: String,
    val timestamp: LocalDateTime,
) {
    constructor(from: Username, to: Username, message: String) : this(
        id = UUID.randomUUID(),
        from = from,
        to = to,
        content = message,
        timestamp = LocalDateTime.now(),
    )
}
