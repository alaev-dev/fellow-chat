package ru.alaev.fellowgigachat.group

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.domain.Username
import ru.alaev.fellowgigachat.group.hander.GroupCreationCommand
import ru.alaev.fellowgigachat.group.hander.GroupCreationCommandHandler

@RestController
class GroupController(
    private val groupCreationCommandHandler: GroupCreationCommandHandler,
) {

    @PostMapping("/groups")
    fun createGroup(@RequestBody request: CreateGroupRequest): CreateGroupResponse {
        return CreateGroupResponse(groupCreationCommandHandler.handle(
            GroupCreationCommand(
                members = request.members.map { Username(it) }
            )
        ).value.toString())
    }
}

data class CreateGroupRequest(
    val members: List<String>,
)

data class CreateGroupResponse(
    val id: String
)