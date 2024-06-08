package ru.alaev.fellowgigachat.chat.history

import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.dto.CommonResponse
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
    ): CommonResponse<List<ChatMessageResponse>> {
        return historyQuery.handler(
            CollectPageableHistoryQuery(
                username = Username(username),
                page = PageRequest.of(pageNumber, pageSize)
            )
        ).map { ChatMessageResponse.from(it) }.toCommonResponse(HISTORY)
    }
}