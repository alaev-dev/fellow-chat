package ru.alaev.fellowgigachat.user.changeStatus.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import ru.alaev.fellowgigachat.chat.ChatHandler
import ru.alaev.fellowgigachat.chat.dto.ResponseType
import ru.alaev.fellowgigachat.chat.dto.status.StatusChangeEventResponse
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.persistence.users.UserStorage
import ru.alaev.fellowgigachat.domain.Username

@Service
class ChangeStatusCommandHandler(
    private val chatHandler: ChatHandler,
    private val userStorage: UserStorage,
    private val objectMapper: ObjectMapper,
) {
    fun handle(command: ChangeStatusCommand) {
        userStorage.changeStatus(Username(command.userId), command.status)

        chatHandler.sessions.forEach {
            it.value.sendMessage(
                TextMessage(
                    StatusChangeEventResponse(
                        command.userId,
                        command.status
                    ).toCommonResponse(ResponseType.STATUS_CHANGE).toJson(objectMapper)
                )
            )
        }
    }
}

data class ChangeStatusCommand(
    val userId: String,
    val status: String,
)