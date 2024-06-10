package ru.alaev.fellowgigachat.chat.persistence.user

import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username

interface UserStorage {
    fun createUser(user: User)
    fun getUser(username: Username): UserEntity?
    fun createEmptyUser(username: Username): UserEntity
    fun changeStatus(username: Username, newStatus: Status)
}