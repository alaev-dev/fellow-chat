package ru.alaev.fellowgigachat.domain

data class User(
    val username: Username,
    val status: Status,
)

@JvmInline
value class Username(val value: String)

@JvmInline
value class Status(val value: String)

