package ru.alaev.fellowgigachat.domain

data class Group(
    val id: GroupId,
    val name: GroupName,
    val members: List<Username>,
)

@JvmInline
value class GroupName(val value: String)

@JvmInline
value class GroupId(val value: Long)
