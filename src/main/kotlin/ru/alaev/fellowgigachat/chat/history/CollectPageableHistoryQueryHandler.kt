package ru.alaev.fellowgigachat.chat.history

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.chat.persistence.chat.CollectPageableHistoryQueryResult
import ru.alaev.fellowgigachat.domain.GroupName

@Service
class CollectPageableHistoryQueryHandler(
    private val chatStorage: ChatStorage,
) {
    fun handler(query: CollectPageableHistoryQuery): CollectPageableHistoryQueryResult {
        return chatStorage.getMessagesPageable(query.groupName, query.page)
    }
}

data class CollectPageableHistoryQuery(
    val groupName: GroupName,
    val page: Pageable
)
