package fr.onat.turboplant.libs.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.math.abs

object DelegatedClock : Clock {
    private var clock: Clock = Clock.System

    override fun now(): Instant = clock.now()

    fun setClock(newClock: Clock) {
        clock = newClock
    }

    fun reset() {
        clock = Clock.System
    }
}

fun Instant.getDisplayableDayCount(): String {
    val dayCountString: (Long) -> String =
        {
            (if (it > 0) "in " else "") +
                    abs(it).toStringWithUnit("day", "days") +
                    (if (it > 0) "" else " ago")
        }

    val timeDeltaHours = (this - DelegatedClock.now()).inWholeHours
    println("instant: $this, delta: $timeDeltaHours")

    return when {
        -24 < timeDeltaHours && timeDeltaHours < 24 -> "today"
        else -> dayCountString(timeDeltaHours/24)
    }
}