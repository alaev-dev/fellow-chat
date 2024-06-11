package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime

data class ChatMessage(
    var id: Long,
    val sender: Username,
    val group: Group,
    val content: String,
    val timestamp: LocalDateTime,
) {
    constructor(from: Username, to: Username, message: String) : this(
        id = -1,
        sender = from,
        group = Group(GroupName(to.value), listOf(from, to)),
        content = message,
        timestamp = LocalDateTime.now(),
    )
}
