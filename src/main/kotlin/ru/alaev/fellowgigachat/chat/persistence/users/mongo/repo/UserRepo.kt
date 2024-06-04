package ru.alaev.fellowgigachat.chat.persistence.users.mongo.repo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.users.mongo.model.UserEntity
import java.util.*

@Repository
interface UserRepo : MongoRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?
}