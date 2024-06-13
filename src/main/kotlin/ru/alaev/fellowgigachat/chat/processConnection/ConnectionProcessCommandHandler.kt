package ru.alaev.fellowgigachat.chat.processConnection

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.socket.WebSocketSession
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.domain.Username

@Service
class ConnectionProcessCommandHandler(
    private val session: SessionManager,
    private val userStorage: UserStorage
) {
    @Transactional
    fun handle(command: ConnectionProcessCommand) {
        session.connectUser(command.session, command.username)
        log.info("New session established: ${command.username.value}")

        // сохранить новое состояние юзера
        val user = userStorage.updateOnline(command.username)
        log.info("User updated: ${user.username.value}")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

data class ConnectionProcessCommand(
    val session: WebSocketSession,
    val username: Username,
)
