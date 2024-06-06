package ru.alaev.fellowgigachat.user.changeStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.user.changeStatus.handler.ChangeStatusCommand
import ru.alaev.fellowgigachat.user.changeStatus.handler.ChangeStatusCommandHandler

@RestController
class ChangeStatusController(
    private val changeStatusCommandHandler: ChangeStatusCommandHandler,
) {

    @PostMapping("/changeStatus")
    fun changeStatus(@RequestBody request: ChangeStatusRequest): ResponseEntity<Unit> {
        changeStatusCommandHandler.handle(ChangeStatusCommand(request.userId, request.status))

        return ResponseEntity.ok().build()
    }
}

data class ChangeStatusRequest(
    val userId: String,
    val status: String,
)