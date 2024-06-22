package ru.alaev.fellowgigachat.chat.persistence.reputation.postgres

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.reputation.ReputationStorage
import ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.model.ReputationEntity
import ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.repo.ReputationRepository
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType
import ru.alaev.fellowgigachat.domain.RepCount
import ru.alaev.fellowgigachat.domain.Reputation
import ru.alaev.fellowgigachat.domain.Username

@Service
class PostgresReputationStorage(
    private val reputationRepository: ReputationRepository,
    private val userStorage: UserStorage
) : ReputationStorage {

    private val logger = LoggerFactory.getLogger(PostgresReputationStorage::class.java)

    override fun getByUsername(username: Username): Reputation? {
        return reputationRepository.findByUsername(username.value)?.toDomain()
    }

    @Transactional
    override fun create(username: Username): Reputation {
        val user = userStorage.getUser(username) ?: run {
            throw DomainException("User ${username.value} not found", ErrorType.NOT_FOUND)
        }
        val entity = ReputationEntity(0, user, 0)

        return reputationRepository.save(entity).toDomain()
    }

    @Transactional
    override fun updateReputation(username: Username, repCount: RepCount): Reputation {
        val rep = reputationRepository.findByUsername(username.value) ?: run {
            throw DomainException("Reputation for user ${username.value} not found", ErrorType.NOT_FOUND)
        }

        rep.count = repCount.value

        return rep.toDomain()
    }
}
