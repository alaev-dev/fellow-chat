package ru.alaev.fellowgigachat.chat.persistence.chat.mongo

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.chat.CollectPageableHistoryQueryResult
import ru.alaev.fellowgigachat.chat.persistence.chat.mongo.model.ChatMessageEntity
import ru.alaev.fellowgigachat.chat.persistence.chat.mongo.repo.ChatMessageRepository
import ru.alaev.fellowgigachat.chat.persistence.users.UserStorage
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Username
import java.util.*

@Service
class MongoChatStorage(
    private val messageRepository: ChatMessageRepository,
    private val userStorage: UserStorage,
) : ChatStorage {
    override fun saveMessage(chatMessage: ChatMessage) {
        val fromUser = userStorage.getUser(chatMessage.sender) ?: userStorage.createEmptyUser(chatMessage.sender)
        val toUser = userStorage.getUser(chatMessage.recipient) ?: userStorage.createEmptyUser(chatMessage.recipient)

        val entity = ChatMessageEntity(
            id = UUID.randomUUID(),
            from = fromUser.id,
            to = toUser.id,
            content = chatMessage.content,
            timestamp = chatMessage.timestamp
        )
        messageRepository.save(entity)
    }

    override fun getMessagesPageable(username: Username, page: Pageable): CollectPageableHistoryQueryResult {
        val user = userStorage.getUser(username) ?: return CollectPageableHistoryQueryResult(
            pages = emptyList(),
            total = 0,
        )

        val skip = page.pageNumber * page.pageSize
        val limit = page.pageSize

        val messages = messageRepository.findLatestMessagesWithUserDetails(user.id, skip, limit)
            .map { entity ->
                ChatMessage(
                    id = entity.id,
                    sender = Username(entity.fromUser.username),
                    recipient = Username(entity.toUser.username),
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }

        val totalMessages = messageRepository.countTotalMessagesForUser(user.id)

        return CollectPageableHistoryQueryResult(
            pages = messages,
            total = totalMessages
        )
    }
}