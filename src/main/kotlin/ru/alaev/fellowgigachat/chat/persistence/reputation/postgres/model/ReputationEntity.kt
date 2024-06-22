package ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.domain.RepCount
import ru.alaev.fellowgigachat.domain.Reputation
import ru.alaev.fellowgigachat.domain.Username

@Entity
@Table(name = "reputations")
class ReputationEntity(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val user: UserEntity,

    @Column(nullable = false)
    var count: Long
) {
    constructor() : this(0, UserEntity(), 0)

    fun toDomain(): Reputation {
        return Reputation(
            username = Username(user.username),
            repCount = RepCount(count)
        )
    }
}
