package ru.alaev.fellowgigachat.domain

import java.time.LocalDateTime

data class User(
    val username: Username,
    val status: Status,
    val groups: List<GroupName>,
    val isOnline: Boolean,
    val lastLoginTimestamp: LocalDateTime?,
)

@JvmInline
value class Username(val value: String)

@JvmInline
value class Status(val value: String)

