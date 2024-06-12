package ru.alaev.fellowgigachat.user.activity.handler

import java.time.LocalDateTime
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.GroupId
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username

@Service
class UserActivityQueryHandler(
    private val userStorage: UserStorage,
    private val chatStorage: ChatStorage,
) {
    @Transactional
    fun handle(query: UserActivityQuery): UserActivity {
        val user = userStorage.getUser(query.username) ?: throw DomainException(
            "user ${query.username} not found", NOT_FOUND
        )

        return UserActivity(
            userStatus = Status(user.status),
            lastMessages = user.groups.mapNotNull { groupEntity ->
                chatStorage.getMessagesPageable(
                    groupId = GroupId(groupEntity.id),
                    page = PageRequest.of(0, 1)
                ).pages.firstOrNull()?.let { LastChatMessage.from(it) }
            }
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
    val id: Long,
    val chatId: Long,
    val sender: Username,
    val chatName: GroupName,
    val members: List<Username>,
    val message: String,
    val timestamp: LocalDateTime,
) {
    companion object {
        fun from(message: ChatMessage): LastChatMessage {
            return LastChatMessage(
                id = message.id,
                chatId = message.group.id.value,
                sender = message.sender,
                chatName = message.group.name,
                members = message.group.members,
                message = message.content,
                timestamp = message.timestamp,
            )
        }
    }
}
