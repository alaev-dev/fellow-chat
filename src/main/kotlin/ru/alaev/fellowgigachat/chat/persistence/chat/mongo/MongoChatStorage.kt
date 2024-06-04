package ru.alaev.fellowgigachat.chat.persistence.chat.mongo

import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
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
        val fromUser = userStorage.getUser(chatMessage.from) ?: userStorage.createEmptyUser(chatMessage.from)
        val toUser = userStorage.getUser(chatMessage.to) ?: userStorage.createEmptyUser(chatMessage.to)

        val entity = ChatMessageEntity(
            id = UUID.randomUUID(),
            from = fromUser.id,
            to = toUser.id,
            content = chatMessage.content,
            timestamp = chatMessage.timestamp
        )
        messageRepository.save(entity)
    }

    override fun getLatestMessages(username: Username): List<ChatMessage> {
        val user = userStorage.getUser(username) ?: return emptyList()

        return messageRepository.findLatestMessagesWithUserDetails(user.id)
            .map { entity ->
                ChatMessage(
                    id = entity.id,
                    from = Username(entity.fromUser.username),
                    to = Username(entity.toUser.username),
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }
    }
}