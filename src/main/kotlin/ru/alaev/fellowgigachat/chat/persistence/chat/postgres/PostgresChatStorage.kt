package ru.alaev.fellowgigachat.chat.persistence.chat.postgres

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.chat.CollectPageableHistoryQueryResult
import ru.alaev.fellowgigachat.chat.persistence.chat.postgres.model.ChatMessageEntity
import ru.alaev.fellowgigachat.chat.persistence.chat.postgres.repo.ChatMessageRepository
import ru.alaev.fellowgigachat.chat.persistence.group.GroupStorage
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.GroupId
import ru.alaev.fellowgigachat.domain.Username

@Service
class PostgresChatStorage(
    private val messageRepository: ChatMessageRepository,
    private val userStorage: UserStorage,
    private val groupStorage: GroupStorage,
) : ChatStorage {

    @Transactional
    override fun saveMessage(chatMessage: ChatMessage): ChatMessage {
        logger.info("Saving message from ${chatMessage.sender} to group ${chatMessage.group.id}")

        val fromUser = userStorage.getUser(chatMessage.sender) ?: run {
            logger.warn("User ${chatMessage.sender} not found, creating an empty user")
            userStorage.createEmptyUser(chatMessage.sender)
        }

        val group = groupStorage.getById(chatMessage.group.id) ?: run {
            logger.error("Group ${chatMessage.group.id.value} not found")
            throw DomainException("Group ${chatMessage.group.id.value} not found", NOT_FOUND)
        }

        val entity = ChatMessageEntity(
            id = 0,
            sender = fromUser,
            group = group,
            content = chatMessage.content,
            timestamp = chatMessage.timestamp,
        )

        val savedMessage = messageRepository.save(entity).toDomain()
        logger.info("Message saved with ID ${savedMessage.id}")

        return savedMessage
    }

    override fun getMessagesPageable(groupId: GroupId, page: Pageable): CollectPageableHistoryQueryResult {
        logger.info("Retrieving pageable messages for group ${groupId.value}")

        val group = groupStorage.getById(groupId)
        if (group == null) {
            logger.warn("Group ${groupId.value} not found")
            return CollectPageableHistoryQueryResult(
                pages = emptyList(),
                total = 0,
            )
        }

        val messages = messageRepository.findLatestMessages(groupId, page)
            .map { entity ->
                ChatMessage(
                    id = entity.id,
                    sender = Username(entity.sender.username),
                    group = entity.group.toDomain(),
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }

        val totalMessages = messageRepository.countTotalMessagesForGroup(groupId)

        logger.info("Found ${messages.size} messages for group ${groupId.value}, total messages: $totalMessages")

        return CollectPageableHistoryQueryResult(
            pages = messages,
            total = totalMessages
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PostgresChatStorage::class.java)
    }
}
