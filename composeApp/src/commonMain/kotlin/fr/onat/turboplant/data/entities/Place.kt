package fr.onat.turboplant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.onat.turboplant.data.dto.PlaceDto

@Entity
data class Place(
    @PrimaryKey val id: String,
    val label: String)

fun PlaceDto.toPlace() = Place(id = id, label = label)