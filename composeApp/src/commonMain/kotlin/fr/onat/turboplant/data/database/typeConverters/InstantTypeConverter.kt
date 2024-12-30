package fr.onat.turboplant.data.database.typeConverters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class InstantTypeConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?): String? {
        return instant?.toString()
    }

    @TypeConverter
    fun toInstant(value: String?): Instant? {
        return value?.let { Instant.parse(it) }
    }
}