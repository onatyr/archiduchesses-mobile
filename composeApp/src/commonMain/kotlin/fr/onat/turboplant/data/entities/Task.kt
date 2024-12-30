package fr.onat.turboplant.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import fr.onat.turboplant.data.dto.TaskDto
import fr.onat.turboplant.data.dto.TaskType
import kotlinx.datetime.Instant

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = arrayOf("id"), childColumns = arrayOf("plantId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @PrimaryKey val id: String,
    val plantId: String,
    val type: TaskType,
    val dueDate: Instant,
    val done: Boolean
)

data class TaskWithPlant(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "plantId",
        entityColumn = "id"
    ) val plant: Plant?,
)

fun TaskDto.toTask() = Task(
    id = id,
    plantId = plantId,
    type = type,
    dueDate = dueDate,
    done = done
)