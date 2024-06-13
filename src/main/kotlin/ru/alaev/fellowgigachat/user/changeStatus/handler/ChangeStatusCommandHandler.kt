package ru.alaev.fellowgigachat.user.changeStatus.handler

import java.time.LocalDateTime
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.notify.NotificationService
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username

@Service
class ChangeStatusCommandHandler(
    private val userStorage: UserStorage,
    private val notificationService: NotificationService
) {
    fun handle(command: ChangeStatusCommand) {
        if (userStorage.getUser(command.username) == null) {
            createUser(command)
        }

        userStorage.changeStatus(command.username, command.status)

        notificationService.notifyStatusChange(command.username, command.status)
    }

    private fun createUser(command: ChangeStatusCommand): User {
        val newUser = User(
            username = command.username,
            status = command.status,
            groups = emptyList(),
            isOnline = true,
            lastLoginTimestamp = LocalDateTime.now(),
        )
        userStorage.createUser(newUser)
        return newUser
    }
}

data class ChangeStatusCommand(
    val username: Username,
    val status: Status,
)