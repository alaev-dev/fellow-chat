package ru.alaev.fellowgigachat.chat.persistence.chat.postgres.repo

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.chat.postgres.model.ChatMessageEntity

@Repository
interface ChatMessageRepository : CrudRepository<ChatMessageEntity, Long> {

    @Query(
        """
        SELECT DISTINCT b FROM ChatMessageEntity b
        WHERE b.group.id = :groupId
        ORDER BY b.timestamp DESC
    """
    )
    fun findLatestMessages(groupId: Long, pageable: Pageable): Page<ChatMessageEntity>
}
