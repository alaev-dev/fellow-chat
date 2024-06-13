package ru.alaev.fellowgigachat.chat.persistence.user.postgres

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.repo.UserRepo
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username

@Service
class PostgresUserStorage(
    private val userRepo: UserRepo
) : UserStorage {
    override fun createUser(user: User) {
        val entity = UserEntity.fromDomain(user)
        userRepo.save(entity)
    }

    override fun getUser(username: Username): UserEntity? {
        return userRepo.findByUsername(username.value)
    }

    override fun createEmptyUser(username: Username): UserEntity {
        val user = UserEntity(
            id = -1,
            username = username.value,
            status = "NEW",
            isOnline = false,
            lastLoginTimestamp = null,
        )
        return userRepo.save(user)
    }

    override fun changeStatus(username: Username, newStatus: Status) {
        val user = userRepo.findByUsername(username.value)
            ?: throw DomainException("user $username not found", NOT_FOUND)

        log.info("Changing status of user ${user.username} from ${user.status} to $newStatus")

        user.status = newStatus.value
        userRepo.save(user)

        val updatedUser = userRepo.findByUsername(username.value)
        log.info("Updated status of user ${updatedUser?.username} is ${updatedUser?.status}")
    }

    override fun updateOnline(username: Username): User {
        val user = getUser(username) ?: createEmptyUser(username)
        user.isOnline = true
        return user.toDomain()
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}