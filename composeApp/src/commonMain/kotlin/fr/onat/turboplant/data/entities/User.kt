package fr.onat.turboplant.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.onat.turboplant.data.dto.UserDto

@Entity
data class User(
    @PrimaryKey val id: String,
//    val name: String?,
    val token: String? = null,
)

fun UserDto.toUser() = User(
    id = id,
//    name = name,
    token = token
)