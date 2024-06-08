package ru.alaev.fellowgigachat.chat.notify

import ru.alaev.fellowgigachat.domain.Status
import ru.alaev.fellowgigachat.domain.Username

fun interface NotificationService {
    fun notifyStatusChange(username: Username, status: Status)
}