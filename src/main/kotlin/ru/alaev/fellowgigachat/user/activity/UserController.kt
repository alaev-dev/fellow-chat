package ru.alaev.fellowgigachat.user.activity

import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.config.DomainException
import ru.alaev.fellowgigachat.config.ErrorType.NOT_FOUND
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username
import ru.alaev.fellowgigachat.user.activity.handler.UserActivity
import ru.alaev.fellowgigachat.user.activity.handler.UserActivityQuery
import ru.alaev.fellowgigachat.user.activity.handler.UserActivityQueryHandler

@RestController
class UserController(
    private val userStorage: UserStorage,
    private val userActivityHandler: UserActivityQueryHandler
) {

    @GetMapping("/users/{username}")
    fun getUser(@PathVariable username: String): UserResponse {
        log.info("Requesting user info for :: $username")
        return UserResponse.from(
            userStorage.getUser(Username(username))
                ?: throw DomainException("User not found", NOT_FOUND)
        )
    }

    @GetMapping("/users/{username}/activity")
    fun getUserActivity(@PathVariable username: String): UserActivityResponse {
        val result = userActivityHandler.handle(UserActivityQuery(Username(username)))

        return UserActivityResponse.from(result)
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserController::class.java)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<Unit> {
        if (userStorage.getUser(Username(request.username)) == null) {
            userStorage.createUser(
                User(
                    username = Username(request.username),
                    status = Status(request.status),
                    groups = emptyList(),
                )
            )
        }

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}

data class UserResponse(
    val id: String,
    val username: String,
    val status: String,
) {
    companion object {
        fun from(user: UserEntity): UserResponse {
            return UserResponse(
                id = user.id.toString(),
                username = user.username,
                status = user.status,
            )
        }
    }
}

data class UserActivityResponse(
    val userStatus: String,
    val lastMessages: List<LastChatMessageResponse>,
) {
    companion object {
        fun from(user: UserActivity): UserActivityResponse {
            return UserActivityResponse(
                userStatus = user.userStatus.value,
                lastMessages = user.lastMessages.map { chatMessage ->
                    LastChatMessageResponse(
                        id = chatMessage.id.toString(),
                        chatName = chatMessage.chatName.value,
                        sender = chatMessage.sender.value,
                        members = chatMessage.members.map { it.value },
                        message = chatMessage.message,
                        timestamp = chatMessage.timestamp,
                    )
                }
            )
        }
    }
}

data class LastChatMessageResponse(
    val id: String,
    val chatName: String,
    val sender: String,
    val members: List<String>,
    val message: String,
    val timestamp: LocalDateTime,
)

data class CreateUserRequest(
    val username: String,
    val status: String,
)
