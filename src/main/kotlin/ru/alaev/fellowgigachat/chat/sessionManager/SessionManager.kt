package ru.alaev.fellowgigachat.chat.sessionManager

import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.dto.CommonResponse
import ru.alaev.fellowgigachat.domain.Username

interface SessionManager {
    fun sendMessageToSession(username: Username, message: CommonResponse<*>)
    fun sendBroadcastMessage(message: CommonResponse<*>)
    fun connectUser(session: WebSocketSession, username: Username)
    fun removeConnection(username: Username)
}