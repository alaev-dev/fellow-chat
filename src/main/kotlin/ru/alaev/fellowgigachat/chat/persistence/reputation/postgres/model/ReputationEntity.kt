package ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.model

import jakarta.persistence.*
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.domain.*

@Entity
@Table(name = "reputation")
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
            count = Count(count)
        )
    }
}
