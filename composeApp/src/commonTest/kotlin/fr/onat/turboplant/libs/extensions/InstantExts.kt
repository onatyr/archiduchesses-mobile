package fr.onat.turboplant.libs.extensions

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class InstantExts {
    @BeforeTest
    fun setClock() {
        DelegatedClock.setClock(object : Clock {
            override fun now(): Instant = Instant.parse("2025-01-20T10:15:30Z")
        })
    }

    @AfterTest
    fun resetClock() {
        DelegatedClock.reset()
    }

    @Test
    fun `should display 'today'`() {
        assertEquals(
            expected = "today",
            actual = Instant.parse("2025-01-20T16:50:19.730740Z").getDisplayableDayCount()
        )
    }

    @Test
    fun `should display prior date`() {
        assertEquals(
            expected = "6 days ago",
            actual = Instant.parse("2025-01-14T09:49:19.730740Z").getDisplayableDayCount()
        )
    }

    @Test
    fun `should display posterior date`() {
        assertEquals(
            expected = "in 8 days",
            actual = Instant.parse("2025-01-28T18:51:19.730740Z").getDisplayableDayCount()
        )
    }
}