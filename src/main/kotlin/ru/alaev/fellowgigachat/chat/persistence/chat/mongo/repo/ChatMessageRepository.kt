package ru.alaev.fellowgigachat.chat.persistence.chat.mongo.repo

import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
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
            // Add a skip stage to handle pagination
            "{ \$skip: ?1 }",
            // Add a limit stage to limit the number of results per page
            "{ \$limit: ?2 }",
            "{ \$sort: { 'timestamp': -1 } }"
        ]
    )
    fun findLatestMessagesWithUserDetails(userId: UUID, skip: Int, limit: Int): List<ChatMessageEntityWithUserDetails>

    @Query("{ \$or: [ { 'to': ?0 }, { 'from': ?0 } ] }")
    fun countTotalMessagesForUser(userId: UUID): Long
}
