package ru.alaev.fellowgigachat.chat.persistence.user.postgres.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import ru.alaev.fellowgigachat.chat.persistence.group.postgres.model.GroupEntity
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.User
import ru.alaev.fellowgigachat.domain.Username

@Entity
@Table(name = "users")
open class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open val id: Long,

    @Column(nullable = false, unique = true)
    open val username: String,

    @Column(nullable = false)
    open var status: String,

    @ManyToMany
    @JoinTable(
        name = "users_groups",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "group_id")]
    )
    open val groups: MutableSet<GroupEntity> = mutableSetOf(),
) {
    fun toDomain(): User {
        return User(
            username = Username(username),
            status = Status(status),
            groups = groups.map { GroupName(it.name) }
        )
    }

    companion object {
        fun fromDomain(user: User): UserEntity {
            return UserEntity(
                id = -1,
                username = user.username.value,
                status = user.status.value
            )
        }
    }

    constructor() : this(-1, "", "")
}
