package ru.alaev.fellowgigachat.chat.sessionManager

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.dto.CommonResponse
import ru.alaev.fellowgigachat.domain.Username
import java.util.concurrent.ConcurrentHashMap

@Service
class WebSocketSessionManager(
    private val objectMapper: ObjectMapper
) : SessionManager {
    private val sessions = ConcurrentHashMap<Username, WebSocketSession>()

    override fun sendMessageToSession(username: Username, message: CommonResponse<*>) {
        sessions[username]?.sendMessage(TextMessage(message.toJson(objectMapper)))
    }

    override fun connectUser(session: WebSocketSession, username: Username) {
        sessions[username] = session
    }

    override fun removeConnection(username: Username) {
        sessions.remove(username)
    }
}
