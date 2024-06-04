package ru.alaev.fellowgigachat.chat.dto

import com.fasterxml.jackson.databind.ObjectMapper

// Enum для типов сообщений
enum class ResponseType {
    MESSAGE,
    STATUS_CHANGE,
    HISTORY
}

// Общий класс для хранения данных
data class CommonResponse<T>(
    val type: ResponseType,
    val data: T,
) {
    fun toJson(objectMapper: ObjectMapper): String {
        return objectMapper.writeValueAsString(this)
    }
}

// Интерфейс для обозначения конвертируемых классов
interface ConvertibleToCommonResponse

// Функции расширения для преобразования в CommonResponse
inline fun <reified T : ConvertibleToCommonResponse> T.toCommonResponse(type: ResponseType): CommonResponse<T> {
    return CommonResponse(
        type = type,
        data = this
    )
}

// Функции расширения для преобразования списка в CommonResponse
inline fun <reified T : ConvertibleToCommonResponse> List<T>.toCommonResponse(type: ResponseType): CommonResponse<List<T>> {
    return CommonResponse(
        type = type,
        data = this
    )
}