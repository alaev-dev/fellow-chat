package ru.alaev.fellowgigachat.user.changeStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username
import ru.alaev.fellowgigachat.user.changeStatus.handler.ChangeStatusCommand
import ru.alaev.fellowgigachat.user.changeStatus.handler.ChangeStatusCommandHandler

@RestController
class ChangeStatusController(
    private val changeStatusCommandHandler: ChangeStatusCommandHandler,
) {

    @PatchMapping("/users/status")
    fun changeStatus(@RequestBody request: ChangeStatusRequest): ResponseEntity<Unit> {
        changeStatusCommandHandler.handle(
            ChangeStatusCommand(
                username = Username(request.username),
                status = Status(request.status)
            )
        )

        return ResponseEntity.ok().build()
    }
}

data class ChangeStatusRequest(
    val username: String,
    val status: String,
)