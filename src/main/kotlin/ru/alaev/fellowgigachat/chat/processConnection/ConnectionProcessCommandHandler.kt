package ru.alaev.fellowgigachat.chat.processConnection

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.processConnection.sendHistory.SendHistoryCommandHandler
import ru.alaev.fellowgigachat.domain.Username
import java.util.concurrent.ConcurrentHashMap

@Service
class ConnectionProcessCommandHandler(
    private val sendHistoryCommandHandler: SendHistoryCommandHandler,
) {
    fun handle(command: ConnectionProcessCommand) {
        command.sessions[command.username] = command.session

        log.info("New session established: ${command.username.value}")

        // sendHistoryCommandHandler.handle(SendHistoryCommand(command.session, command.username))
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ConnectionProcessCommand(
    val session: WebSocketSession,
    val username: Username,
    val sessions: ConcurrentHashMap<Username, WebSocketSession>,
)
