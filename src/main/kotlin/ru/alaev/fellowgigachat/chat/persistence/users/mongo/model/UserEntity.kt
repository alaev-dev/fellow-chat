package ru.alaev.fellowgigachat.chat.persistence.users.mongo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username
import java.util.*

@Document(collection = "users")
data class UserEntity(
    @Id val id: UUID,
    val username: String,
    var status: String
) {
    fun toDomain(): User {
        return User(
            username = Username(username),
            status = Status(status)
        )
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                id = UUID.randomUUID(),
                username = user.username.value,
                status = user.status.value
            )
        }
    }
}