package ru.alaev.fellowgigachat.group

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.persistence.group.GroupStorage
import ru.alaev.fellowgigachat.domain.Username

@RestController
class GroupController(
    private val storage: GroupStorage
) {

    @PostMapping("/groups")
    fun createGroup(@RequestBody request: CreateGroupRequest): CreateGroupResponse {
        return CreateGroupResponse(storage.createGroupForMembers(request.members.map { Username(it) }).id.toString())
    }
}

data class CreateGroupRequest(
    val members: List<String>,
)

data class CreateGroupResponse(
    val id: String
)