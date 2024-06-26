package ru.alaev.fellowgigachat.chat.persistence.chat.postgres.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import ru.alaev.fellowgigachat.chat.persistence.group.postgres.model.GroupEntity
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity
import ru.alaev.fellowgigachat.domain.ChatMessage

@Entity
@Table(name = "messages")
open class ChatMessageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    open val id: Long = 0,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    open val sender: UserEntity,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "group_id")
    open val group: GroupEntity,

    @Column(nullable = false)
    open val content: String,

    @Column(nullable = false)
    open val timestamp: LocalDateTime,

    @Column(nullable = false)
    open var isRead: Boolean,
) {
    fun toDomain(): ChatMessage {
        return ChatMessage(
            id = this.id,
            sender = this.sender.toDomain().username,
            group = group.toDomain(),
            content = this.content,
            timestamp = this.timestamp,
            isRead = this.isRead,
        )
    }

    constructor() : this(0, UserEntity(), GroupEntity(), "", LocalDateTime.MIN, false)

    companion object {
        fun from(chatMessage: ChatMessage, fromUser: UserEntity, group: GroupEntity): ChatMessageEntity {
            return ChatMessageEntity(
                id = 0,
                sender = fromUser,
                group = group,
                content = chatMessage.content,
                timestamp = chatMessage.timestamp,
                isRead = chatMessage.isRead,
            )
        }
    }
}