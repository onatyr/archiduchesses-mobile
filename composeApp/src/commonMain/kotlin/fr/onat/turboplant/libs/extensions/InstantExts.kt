package fr.onat.turboplant.libs.extensions

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
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

val Instant.deltaFromNowHours: Long
    get() = (this - DelegatedClock.now()).inWholeHours

val Instant.deltaFromNowDays: Long
    get() = deltaFromNowHours / 24

fun Instant.isPast() = deltaFromNowHours < -24
fun Instant.isToday() = deltaFromNowHours in -24..24
fun Instant.isInNextDays(days: Int) =
    this in DelegatedClock.now()..Instant.fromEpochSeconds(DelegatedClock.now().epochSeconds + days * 24 * 3600)

@Composable
fun Instant.getDisplayableDayCount(): String {

    val toStringWithUnit: @Composable (Long) -> String = {
        abs(it).toStringWithUnit(stringResource(Res.string.day), stringResource(Res.string.days))
    }

    return when {
        isPast() -> stringResource(Res.string.past_instant, toStringWithUnit(deltaFromNowDays))
        isToday() -> stringResource(Res.string.today)
        else -> stringResource(Res.string.future_instant, toStringWithUnit(deltaFromNowDays))
    }
}

fun Instant.formatToString(): String {
    val localDateTime = toLocalDateTime(TimeZone.UTC)
    return "${
        localDateTime.month.name.substring(0, 3).onlyFirstCharUppercase()
    } ${localDateTime.dayOfMonth}, ${localDateTime.year}"
}