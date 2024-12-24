package fr.onat.turboplant.data.dto

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import fr.onat.turboplant.data.dto.Sunlight as SunlightEnum

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
    val fieldName: String,
    val update: MutableStateFlow<NewPlantDto>.(T) -> Unit
) {
    data object Name : NewPlantField<String?>(
        fieldName = "name",
        update = { value -> update { it.copy(name = value) } })

    data object Species : NewPlantField<String?>(
        fieldName = "species",
        update = { value -> update { it.copy(species = value) } }
    )

    data object Sunlight : NewPlantField<SunlightEnum?>(
        fieldName = "sunlight",
        update = { value -> update { it.copy(sunlight = value) } })

    data object WateringRecurrenceDays : NewPlantField<Int?>(
        fieldName = "wateringRecurrenceDays",
        update = { value -> update { it.copy(wateringRecurrenceDays = value) } }
    )

    data object AdoptionDate : NewPlantField<String?>(
        fieldName = "adoptionDate",
        update = { value -> update { it.copy(adoptionDate = value) } }
    )

    data object RoomId : NewPlantField<String?>(
        fieldName = "roomId",
        update = { value -> update { it.copy(roomId = value) } }
    )

    data object ImageUrl : NewPlantField<String?>(
        fieldName = "imageUrl",
        update = { value -> update { it.copy(imageUrl = value) } }
    )
}