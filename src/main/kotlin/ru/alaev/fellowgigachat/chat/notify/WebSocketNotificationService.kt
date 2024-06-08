package ru.alaev.fellowgigachat.chat.notify

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import ru.alaev.fellowgigachat.chat.ChatHandler
import ru.alaev.fellowgigachat.chat.dto.ResponseType
import ru.alaev.fellowgigachat.chat.dto.status.StatusChangeEventResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username

@Service
class WebSocketNotificationService(
    private val chatHandler: ChatHandler,
    private val objectMapper: ObjectMapper,
) : NotificationService {
    override fun notifyStatusChange(username: Username, status: Status) {
        val response = StatusChangeEventResponse(
            username.value,
            status.value
        ).toCommonResponse(ResponseType.STATUS_CHANGE)

        val message = TextMessage(response.toJson(objectMapper))

        chatHandler.sessions.forEach { session ->
            session.value.sendMessage(message)
        }
    }
}