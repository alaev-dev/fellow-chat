package ru.alaev.fellowgigachat.chat.persistence

import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.UserId

interface ChatStorage {
    fun saveMessage(chatMessage: ChatMessage)
    fun getLatestMessages(userId: UserId): List<ChatMessage>
}