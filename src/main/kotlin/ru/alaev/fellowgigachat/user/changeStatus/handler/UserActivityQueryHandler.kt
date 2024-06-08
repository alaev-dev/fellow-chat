package ru.alaev.fellowgigachat.user.changeStatus.handler

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.users.mongo.repo.UserRepo
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username
import java.time.LocalDateTime
import java.util.*

@Service
class UserActivityQueryHandler(
    private val userRepo: UserRepo,
    private val chatStorage: ChatStorage,
) {
    fun handle(query: UserActivityQuery): UserActivity {
        val user = userRepo.findByUsername(query.username) ?: throw DomainException(
            "user ${query.username} not found", NOT_FOUND
        )

        val lastMessages = chatStorage.getMessagesPageable(query.username, PageRequest.of(0, 1))

        UserActivity(
            userStatus = user.status,
            lastMessages = lastMessages.pages.map { }
        )
    }
}

data class UserActivityQuery(
    val username: Username,
)

data class UserActivity(
    val userStatus: Status,
    val lastMessages: List<LastChatMessage>,
)

data class LastChatMessage(
    val id: UUID,
    val friendUsername: Username,
    val message: String,
    val timestamp: LocalDateTime,
) {
    companion object {
        fun from(message: ChatMessage): LastChatMessage {
            return LastChatMessage(
                id = message.id,
                friendUsername =
            )
        }
    }
}
