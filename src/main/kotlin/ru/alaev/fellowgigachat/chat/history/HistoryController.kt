package ru.alaev.fellowgigachat.chat.history

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.dto.CommonResponse
import ru.alaev.fellowgigachat.chat.dto.ResponseType.HISTORY
import ru.alaev.fellowgigachat.chat.dto.message.ChatMessageResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.persistence.chat.ChatStorage
import ru.alaev.fellowgigachat.domain.Username

@RestController
class HistoryController(
    private val chatStorage: ChatStorage,
) {

    @GetMapping("/history/{username}")
    fun getHistory(@PathVariable username: String): CommonResponse<List<ChatMessageResponse>> {
        return chatStorage.getLatestMessages(Username(username))
            .map { ChatMessageResponse.from(it) }.toCommonResponse(HISTORY)
    }

}