package fr.onat.turboplant.libs.extensions

import androidx.compose.runtime.Composable
import fr.onat.turboplant.logger.logger
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import turboplant.composeapp.generated.resources.Res
import turboplant.composeapp.generated.resources.day
import turboplant.composeapp.generated.resources.days
import turboplant.composeapp.generated.resources.future_instant
import turboplant.composeapp.generated.resources.past_instant
import turboplant.composeapp.generated.resources.today
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

@Composable
fun Instant.getDisplayableDayCount(): String {

    val toStringWithUnit: @Composable (Long) -> String = {
        abs(it).toStringWithUnit(stringResource(Res.string.day), stringResource(Res.string.days))
    }

    val dayCountString: @Composable (Long) -> String =
        {
            if (it > 0) stringResource(Res.string.future_instant, toStringWithUnit(it))
            else stringResource(Res.string.past_instant, toStringWithUnit(it))
        }

    val timeDeltaHours = (this - DelegatedClock.now()).inWholeHours

    return when {
        -24 < timeDeltaHours && timeDeltaHours < 24 -> stringResource(Res.string.today)
        else -> dayCountString(timeDeltaHours / 24)
    }
}

fun Instant.formatToString(): String {
    val localDateTime = toLocalDateTime(TimeZone.UTC)
    return "${
        localDateTime.month.name.substring(0, 3).onlyFirstCharUppercase()
    } ${localDateTime.dayOfMonth}, ${localDateTime.year}"
}