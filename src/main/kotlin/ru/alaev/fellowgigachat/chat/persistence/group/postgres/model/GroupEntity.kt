package ru.alaev.fellowgigachat.chat.persistence.group.postgres.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.domain.Group
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Username

@Entity
@Table(name = "groups")
open class GroupEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open val id: Long = 0,

    @Column(nullable = false, unique = true)
    open val name: String,

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_groups",
        joinColumns = [JoinColumn(name = "group_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    open val users: MutableSet<UserEntity> = mutableSetOf(),
) {
    constructor() : this(0, "")

    fun toDomain(): Group {
        return Group(
            name = GroupName(name),
            members = users.map { Username(it.username) }
        )
    }
}
