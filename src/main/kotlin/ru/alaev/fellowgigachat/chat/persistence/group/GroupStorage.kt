package ru.alaev.fellowgigachat.chat.persistence.group

import ru.alaev.fellowgigachat.chat.persistence.group.postgres.model.GroupEntity
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Username

interface GroupStorage {
    fun getOrCreate(groupName: GroupName, users: List<Username>): GroupEntity
    fun get(groupName: GroupName): GroupEntity?
    fun getMembers(groupName: GroupName): List<Username>
}