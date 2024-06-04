package ru.alaev.fellowgigachat.chat.persistence.chat

import ru.alaev.fellowgigachat.domain.ChatMessage
import ru.alaev.fellowgigachat.domain.Username

interface ChatStorage {
    fun saveMessage(chatMessage: ChatMessage)
    fun getLatestMessages(username: Username): List<ChatMessage>
}