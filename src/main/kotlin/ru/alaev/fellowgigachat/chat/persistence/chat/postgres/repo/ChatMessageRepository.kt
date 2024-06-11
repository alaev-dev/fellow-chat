package ru.alaev.fellowgigachat.chat.persistence.chat.postgres.repo

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.chat.postgres.model.ChatMessageEntity
import ru.alaev.fellowgigachat.domain.GroupName

@Repository
interface ChatMessageRepository : CrudRepository<ChatMessageEntity, Long> {

    @Query(
        """
        SELECT b FROM ChatMessageEntity b
        WHERE b.group.name = :groupName
        ORDER BY b.timestamp DESC
    """
    )
    fun findLatestMessages(groupName: GroupName, pageable: Pageable): List<ChatMessageEntity>

    @Query(
        """
        SELECT COUNT(m) FROM ChatMessageEntity m 
        WHERE m.group.name = :groupName
    """
    )
    fun countTotalMessagesForGroup(groupName: GroupName): Long
}
