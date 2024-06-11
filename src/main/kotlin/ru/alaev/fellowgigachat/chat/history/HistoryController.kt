package ru.alaev.fellowgigachat.chat.history

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.domain.GroupId

@RestController
class HistoryController(
    private val historyQuery: CollectPageableHistoryQueryHandler,
) {

    @GetMapping("/history/{groupId}")
    fun getHistory(
        @PathVariable groupId: Long,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int
    ): PageableHistoryResponse {
        val result = historyQuery.handler(
            CollectPageableHistoryQuery(
                groupId = GroupId(groupId),
                page = PageRequest.of(pageNumber, pageSize)
            )
        )

        return PageableHistoryResponse(
            page = result.pages.map { ChatMessageResponse.from(it) },
            total = result.total
        )
    }
}

data class PageableHistoryResponse(
    val page: List<ChatMessageResponse>,
    val total: Long,
) : ConvertibleToCommonResponse