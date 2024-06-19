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
import ru.alaev.fellowgigachat.domain.*

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
            logger.error("User ${username.value} not found")
            throw DomainException("User ${username.value} not found", ErrorType.NOT_FOUND)
        }
        val entity = ReputationEntity(0, user, 0)

        return reputationRepository.save(entity).toDomain()
    }

    @Transactional
    override fun updateReputation(username: Username, count: Count): Reputation {
        val rep = reputationRepository.findByUsername(username.value) ?: run {
            logger.error("Reputation for user ${username.value} not found")
            throw DomainException("Reputation for user ${username.value} not found", ErrorType.NOT_FOUND)
        }

        rep.count = count.value

        return rep.toDomain()
    }
}
