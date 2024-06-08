package ru.alaev.fellowgigachat.chat.notify

import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.dto.ResponseType
import ru.alaev.fellowgigachat.chat.dto.status.StatusChangeEventResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username

@Service
class WebSocketNotificationService(
    private val sessionManager: SessionManager
) : NotificationService {
    override fun notifyStatusChange(username: Username, status: Status) {
        val event = StatusChangeEventResponse(
            username.value,
            status.value
        ).toCommonResponse(ResponseType.STATUS_CHANGE)

        sessionManager.sendBroadcastMessage(event)
    }
}