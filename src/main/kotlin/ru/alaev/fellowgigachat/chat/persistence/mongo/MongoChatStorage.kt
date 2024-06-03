package ru.alaev.fellowgigachat.chat.persistence.mongo

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.mongo.model.ChatMessageEntity
import ru.alaev.fellowgigachat.chat.persistence.mongo.repo.ChatMessageRepository
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId

@Service
class MongoChatStorage(
    private val repository: ChatMessageRepository
) : ChatStorage {
    override fun saveMessage(chatMessage: ChatMessage) {
        val entity = ChatMessageEntity(
            id = chatMessage.id,
            from = chatMessage.from.value,
            to = chatMessage.to.value,
            content = chatMessage.content,
            timestamp = chatMessage.timestamp
        )
        repository.save(entity)
    }

    override fun getLatestMessages(userId: UserId): List<ChatMessage> {
        val pageable = PageRequest.of(0, 50)
        return repository.findLatestMessagesBy(userId.value, pageable)
            .map { entity ->
                ChatMessage(
                    id = entity.id,
                    from = UserId(entity.from),
                    to = UserId(entity.to),
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }
    }
}