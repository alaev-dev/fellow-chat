package ru.alaev.fellowgigachat.chat.processDisconnect

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.Username

@Service
class ProcessDisconnectCommandHandler(
    private val userStorage: UserStorage,
    private val sessionManager: SessionManager,
) {
    @Transactional
    fun handle(command: ProcessDisconnectCommand) {
        val user = userStorage.getUser(command.username)
            ?: throw DomainException("User ${command.username} not found", NOT_FOUND)

        user.isOnline = false
        user.lastLoginTimestamp = LocalDateTime.now()

        sessionManager.removeConnection(command.username)
    }
}

data class ProcessDisconnectCommand(
    val username: Username,
)