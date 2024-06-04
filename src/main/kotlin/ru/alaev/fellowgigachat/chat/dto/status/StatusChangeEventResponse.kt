package ru.alaev.fellowgigachat.chat.dto.status

import ru.alaev.fellowgigachat.chat.dto.ConvertibleToCommonResponse

data class StatusChangeEventResponse(
    val userId: String,
    val status: String,
) : ConvertibleToCommonResponse
