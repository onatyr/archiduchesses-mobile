package fr.onat.turboplant.data.dto

import fr.onat.turboplant.libs.extensions.toStringOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable
import fr.onat.turboplant.data.dto.Sunlight as SunlightEnum

@Serializable
data class NewPlantDto(
    val name: String? = null,
    val species: String? = null,
    val sunlight: SunlightEnum? = null,
    val wateringRecurrenceDays: Int? = null,
    val adoptionDate: String? = null,
    val roomId: String? = null,
    val imageUrl: String? = null
)

sealed class NewPlantField<T>(
    val value: NewPlantDto.() -> String,
    val update: MutableStateFlow<NewPlantDto>.(String) -> Unit,
) {
    data object Name : NewPlantField<String?>(
        value = { name ?: "" },
        update = { value -> update { it.copy(name = value) } },
    )

    data object Species : NewPlantField<String?>(
        value = { species ?: "" },
        update = { value -> update { it.copy(species = value) } }
    )

    data object Sunlight : NewPlantField<SunlightEnum?>(
        value = { sunlight?.textValue ?: "" },
        update = { value -> update { it.copy(sunlight = value.toSunlightEnumOrNull()) } })

    data object WateringRecurrenceDays : NewPlantField<Int?>(
        value = { wateringRecurrenceDays.toStringOrNull() ?: "" },
        update = { value -> update { it.copy(wateringRecurrenceDays = value.toIntOrNull()) } }
    )

    data object AdoptionDate : NewPlantField<String?>(
        value = { adoptionDate ?: "" },
        update = { value -> update { it.copy(adoptionDate = value) } }
    )

    data object RoomId : NewPlantField<String?>(
        value = { roomId ?: "" },
        update = { value -> update { it.copy(roomId = value) } }
    )

    data object ImageUrl : NewPlantField<String?>(
        value = { imageUrl ?: "" },
        update = { value -> update { it.copy(imageUrl = value) } }
    )
}