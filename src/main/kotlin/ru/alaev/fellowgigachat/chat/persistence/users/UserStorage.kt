package ru.alaev.fellowgigachat.chat.persistence.users

import ru.alaev.fellowgigachat.chat.persistence.users.mongo.model.UserEntity
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username

interface UserStorage {
    fun createUser(user: User)
    fun getUser(username: Username): UserEntity?
    fun createEmptyUser(username: Username): UserEntity
    fun changeStatus(username: Username, newStatus: String)
}