package ru.alaev.fellowgigachat.chat.persistence.mongo.repo

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.mongo.model.ChatMessageEntity
import java.util.*

@Repository
interface ChatMessageRepository : MongoRepository<ChatMessageEntity, UUID> {
    @Query("{ \$or: [ { 'to': ?0 }, { 'from': ?0 } ] }")
    fun findLatestMessagesBy(userId: String, pageable: Pageable): List<ChatMessageEntity>
}