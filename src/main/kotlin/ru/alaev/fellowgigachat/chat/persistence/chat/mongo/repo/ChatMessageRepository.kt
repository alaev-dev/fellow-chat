package ru.alaev.fellowgigachat.chat.persistence.chat.mongo.repo

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.chat.mongo.model.ChatMessageEntity
import java.util.*

@Repository
interface ChatMessageRepository : MongoRepository<ChatMessageEntity, UUID> {

    @Aggregation(
        pipeline = [
            "{ \$match: { \$or: [ { 'to': ?0 }, { 'from': ?0 } ] } }",
            "{ \$lookup: { from: 'users', localField: 'from', foreignField: '_id', as: 'fromUser' } }",
            "{ \$lookup: { from: 'users', localField: 'to', foreignField: '_id', as: 'toUser' } }",
            "{ \$unwind: '\$fromUser' }",
            "{ \$unwind: '\$toUser' }",
            "{ \$limit: 50 }",
            "{ \$sort: { 'timestamp': -1 } }"
        ]
    )
    fun findLatestMessagesWithUserDetails(userId: UUID): List<ChatMessageEntityWithUserDetails>
}