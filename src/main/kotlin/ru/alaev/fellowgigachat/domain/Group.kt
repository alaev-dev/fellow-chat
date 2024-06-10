package ru.alaev.fellowgigachat.domain

data class Group(
    val name: GroupName,
    val members: List<Username>,
)

@JvmInline
value class GroupName(val value: String)
