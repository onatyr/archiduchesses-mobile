package fr.onat.turboplant.data.dto

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import fr.onat.turboplant.data.dto.Sunlight as SunlightEnum

@Serializable
data class NewPlantDto(
    val name: String? = null,
    val species: String? = null,
    val sunlight: SunlightEnum? = null,
    val wateringRecurrenceDays: Int? = null,
    val adoptionDate: Instant = Instant.parse("2025-01-03T16:50:19.730740Z"),
    val roomId: String? = null,
    val imageUrl: String? = null
)

sealed class NewPlantField<T>(
    val update: MutableStateFlow<NewPlantDto>.(String) -> Unit,
) {
    data object Name : NewPlantField<String?>(
        update = { value -> update { it.copy(name = value) } },
    )

    data object Species : NewPlantField<String?>(
        update = { value -> update { it.copy(species = value) } }
    )

    data object Sunlight : NewPlantField<SunlightEnum?>(
        update = { value -> update { it.copy(sunlight = value.toSunlightEnumOrNull()) } })

    data object WateringRecurrenceDays : NewPlantField<Int?>(
        update = { value -> update { it.copy(wateringRecurrenceDays = value.toIntOrNull()) } }
    )

    data object AdoptionDate : NewPlantField<String?>(
        update = { value -> update { it.copy(adoptionDate = Instant.parse(value)) } }
    )

    data object RoomId : NewPlantField<String?>(
        update = { value -> update { it.copy(roomId = value) } }
    )

    data object ImageUrl : NewPlantField<String?>(
        update = { value -> update { it.copy(imageUrl = value) } }
    )
}