package ru.alaev.fellowgigachat.chat.history

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.chat.CollectPageableHistoryQueryResult
import ru.alaev.fellowgigachat.domain.GroupId

@Service
class CollectPageableHistoryQueryHandler(
    private val chatStorage: ChatStorage,
) {
    @Transactional(readOnly = true)
    fun handler(query: CollectPageableHistoryQuery): CollectPageableHistoryQueryResult {
        return chatStorage.getMessagesPageable(query.groupId, query.page)
    }
}

data class CollectPageableHistoryQuery(
    val groupId: GroupId,
    val page: Pageable
)
