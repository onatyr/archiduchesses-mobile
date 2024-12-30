package fr.onat.turboplant.data.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    val id: String,
    val plantId: String,
    val type: TaskType,
    val dueDate: Instant,
    val done: Boolean
)

enum class TaskType {
    @SerialName("watering")
    WATERING
}