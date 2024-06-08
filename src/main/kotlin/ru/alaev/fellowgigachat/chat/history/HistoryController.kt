package ru.alaev.fellowgigachat.chat.history

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.dto.CommonResponse
import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse
import ru.alaev.fellowgigachat.chat.dto.ResponseType.HISTORY
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.domain.Username

@RestController
class HistoryController(
    private val historyQuery: CollectPageableHistoryQueryHandler,
) {

    @GetMapping("/history/{username}")
    fun getHistory(
        @PathVariable username: String,
        @RequestParam pageNumber: Int,
        @RequestParam pageSize: Int
    ): CommonResponse<PageableHistoryResponse> {
        val result = historyQuery.handler(
            CollectPageableHistoryQuery(
                username = Username(username),
                page = PageRequest.of(pageNumber, pageSize)
            )
        )

        return PageableHistoryResponse(
            pages = result.pages.map { ChatMessageResponse.from(it) },
            total = result.total
        ).toCommonResponse(HISTORY)
    }
}

data class PageableHistoryResponse(
    val pages: List<ChatMessageResponse>,
    val total: Long,
) : ConvertibleToCommonResponse