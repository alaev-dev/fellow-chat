package ru.alaev.fellowgigachat.domain

data class Reputation(
    val username: Username,
    val repCount: RepCount,
)

@JvmInline
value class RepCount(val value: Long) {
    companion object {
        fun fromString(repCount: String): RepCount {
            return RepCount(repCount.toLong())
        }
    }
}
