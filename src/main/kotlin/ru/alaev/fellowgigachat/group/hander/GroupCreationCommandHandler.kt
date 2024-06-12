package ru.alaev.fellowgigachat.group.hander

import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse
import ru.alaev.fellowgigachat.chat.dto.ResponseType.GROUP_CREATE
import ru.alaev.fellowgigachat.chat.dto.toCommonResponse
import ru.alaev.fellowgigachat.chat.persistence.group.GroupStorage
import ru.alaev.fellowgigachat.chat.sessionManager.SessionManager
import ru.alaev.fellowgigachat.domain.GroupId
import ru.alaev.fellowgigachat.domain.Username

@Service
class GroupCreationCommandHandler(
    private val storage: GroupStorage,
    private val sessionManager: SessionManager
) {
    fun handle(command: GroupCreationCommand): GroupId {
        val group = storage.createGroupForMembers(command.members).toDomain()

        val groupCreationEvent = GroupCreationEvent(
            id = group.id.value.toString(),
            members = group.members.map { it.value },
        ).toCommonResponse(GROUP_CREATE)

        group.members.forEach { username ->
            sessionManager.sendMessageToSession(
                username, groupCreationEvent
            )
        }

        return group.id
    }
}

data class GroupCreationCommand(
    val members: List<Username>,
)

data class GroupCreationEvent(
    val id: String,
    val members: List<String>,
) : ConvertibleToCommonResponse