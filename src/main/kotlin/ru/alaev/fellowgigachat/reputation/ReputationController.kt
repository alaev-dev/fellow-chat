package ru.alaev.fellowgigachat.reputation

import org.springframework.web.bind.annotation.*
import ru.alaev.fellowgigachat.domain.RepCount
import ru.alaev.fellowgigachat.domain.Username
import ru.alaev.fellowgigachat.reputation.handler.ReputationCommandHandler
import ru.alaev.fellowgigachat.reputation.handler.ReputationQueryCommand
import ru.alaev.fellowgigachat.reputation.handler.ReputationUpdateCommand

@RestController
class ClickerController(
    private val reputationCommandHandler: ReputationCommandHandler
) {

    @GetMapping("/reputation/{username}")
    fun getReputation(@PathVariable username: String): ReputationResponse {
        val res = reputationCommandHandler.handle(
            ReputationQueryCommand(Username(username))
        )

        return ReputationResponse(
            res.username.value,
            res.repCount.value
        )
    }

    @PatchMapping("/reputation/{username}")
    fun putReputation(
        @PathVariable username: String,
        @RequestBody request: ReputationUpdateRequest
    ): ReputationResponse {
        val res = reputationCommandHandler.handle(
            ReputationUpdateCommand(
                Username(username),
                RepCount(request.count)
            )
        )

        return ReputationResponse(
            res.username.value,
            res.repCount.value
        )
    }
}

data class ReputationUpdateRequest(
    val count: Long,
)

data class ReputationResponse(
    val username: String,
    val count: Long,
)
