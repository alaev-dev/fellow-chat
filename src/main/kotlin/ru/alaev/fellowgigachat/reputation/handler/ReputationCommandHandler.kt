package ru.alaev.fellowgigachat.reputation.handler

import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.reputation.ReputationStorage
import ru.alaev.fellowgigachat.domain.Count
import ru.alaev.fellowgigachat.domain.Reputation
import ru.alaev.fellowgigachat.domain.Username

@Service
class ReputationCommandHandler(
    private val reputationStorage: ReputationStorage,
) {
    fun handle(command: ReputationUpdateCommand): Reputation {
        return reputationStorage.updateReputation(command.username, command.count)
    }

    fun handle(command: ReputationQueryCommand): Reputation {
        return reputationStorage.getByUsername(command.username)
            ?: reputationStorage.create(command.username)
    }
}

data class ReputationUpdateCommand(
    val username: Username,
    val count: Count,
)

data class ReputationQueryCommand(
    val username: Username,
)