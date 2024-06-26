package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime

data class ChatMessage(
    var id: Long,
    val sender: Username,
    val group: Group,
    val content: String,
    val timestamp: LocalDateTime,
    val isRead: Boolean
) {
    constructor(from: Username, to: GroupId, message: String) : this(
        id = -1,
        sender = from,
        group = Group(to, GroupName("PARASHA"), emptyList()),
        content = message,
        timestamp = LocalDateTime.now(),
        isRead = false
    )
}
