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
import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Group
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Username

@Service
class PostgresChatStorage(
    private val messageRepository: ChatMessageRepository,
    private val userStorage: UserStorage,
    private val groupStorage: GroupStorage,
) : ChatStorage {

    @Transactional
    override fun saveMessage(chatMessage: ChatMessage) {
        val fromUser = userStorage.getUser(chatMessage.sender) ?: userStorage.createEmptyUser(chatMessage.sender)
        val group = groupStorage.getOrCreate(chatMessage.group.name, chatMessage.group.members)
        logger.info("Group ${chatMessage.group.name} created")

        val entity = ChatMessageEntity(
            id = 0,
            sender = fromUser,
            group = group,
            content = chatMessage.content,
            timestamp = chatMessage.timestamp,
        )
        messageRepository.save(entity)
    }

    override fun getMessagesPageable(groupName: GroupName, page: Pageable): CollectPageableHistoryQueryResult {
        groupStorage.get(groupName) ?: return CollectPageableHistoryQueryResult(
            pages = emptyList(),
            total = 0,
        )

        val messages = messageRepository.findLatestMessages(groupName, page)
            .map { entity ->
                ChatMessage(
                    id = entity.id,
                    sender = Username(entity.sender.username),
                    group = Group(GroupName(entity.group.name), entity.group.users.map { Username(it.username) }),
                    content = entity.content,
                    timestamp = entity.timestamp
                )
            }

        val totalMessages = messageRepository.countTotalMessagesForGroup(groupName)

        return CollectPageableHistoryQueryResult(
            pages = messages,
            total = totalMessages
        )
    }

    companion object {
        private val logger = LoggerFactory.getLogger(PostgresChatStorage::class.java)
    }
}