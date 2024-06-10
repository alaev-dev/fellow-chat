package ru.alaev.fellowgigachat.domain

data class User(
    val username: Username,
    val status: Status,
    val groups: List<GroupName>,
)

@JvmInline
value class Username(val value: String)

@JvmInline
value class Status(val value: String)

