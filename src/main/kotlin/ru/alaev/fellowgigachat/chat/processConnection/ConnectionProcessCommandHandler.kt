package ru.alaev.fellowgigachat.chat.processConnection

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.processConnection.sendHistory.SendHistoryCommand
import ru.alaev.fellowgigachat.chat.processConnection.sendHistory.SendHistoryCommandHandler
import ru.alaev.fellowgigachat.domain.UserId
import java.util.concurrent.ConcurrentHashMap

@Service
class ConnectionProcessCommandHandler(
    private val sendHistoryCommandHandler: SendHistoryCommandHandler,
) {
    fun handle(command: ConnectionProcessCommand) {
        command.sessions[command.userId] = command.session

        log.info("New session established: ${command.userId.value}")

        sendHistoryCommandHandler.handle(SendHistoryCommand(command.session, command.userId))
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ConnectionProcessCommand(
    val session: WebSocketSession,
    val userId: UserId,
    val sessions: ConcurrentHashMap<UserId, WebSocketSession>,
)
