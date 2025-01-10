package fr.onat.turboplant.libs.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.abs

fun Instant.getDisplayableDayCount(): String {
    val dayCountString: (Long) -> String =
        {
            (if (it > 0) "in " else "") +
                    abs(it).toStringWithUnit("day", "days") +
                    (if (it > 0) "" else " ago")
        }

    val timeDeltaDays = (this - Clock.System.now()).inWholeDays
    return when {
        timeDeltaDays < -1 -> dayCountString(timeDeltaDays)
        timeDeltaDays > 1 -> dayCountString(timeDeltaDays)
        else -> "today"
    }
}