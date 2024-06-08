package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime
import java.util.*

data class ChatMessage(
    val id: UUID,
    val sender: Username,
    val recipient: Username,
    val content: String,
    val timestamp: LocalDateTime,
) {
    constructor(from: Username, to: Username, message: String) : this(
        id = UUID.randomUUID(),
        sender = from,
        recipient = to,
        content = message,
        timestamp = LocalDateTime.now(),
    )
}
