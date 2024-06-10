package ru.alaev.fellowgigachat.chat.persistence.user.postgres.repo

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity

@Repository
interface UserRepo : CrudRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
}