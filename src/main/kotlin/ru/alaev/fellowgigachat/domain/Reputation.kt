package ru.alaev.fellowgigachat.domain

data class Reputation(
    val username: Username,
    val count: Count,
)

@JvmInline
value class Count(val value: Long) {
    companion object {
        fun fromString(count: String): Count {
            return Count(count.toLong())
        }
    }
}
