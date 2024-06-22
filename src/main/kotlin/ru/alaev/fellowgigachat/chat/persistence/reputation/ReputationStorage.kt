package ru.alaev.fellowgigachat.chat.persistence.reputation

import ru.alaev.fellowgigachat.domain.RepCount
import ru.alaev.fellowgigachat.domain.Reputation
import ru.alaev.fellowgigachat.domain.Username

interface ReputationStorage {

    fun create(username: Username): Reputation

    fun getByUsername(username: Username): Reputation?

    fun updateReputation(username: Username, repCount: RepCount): Reputation
}
