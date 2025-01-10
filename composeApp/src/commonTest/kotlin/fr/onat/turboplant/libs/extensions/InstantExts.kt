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
        val fixedInstant = Instant.parse("2025-01-20T10:15:30Z")
        DelegatedClock.setClock(object : Clock {
            override fun now(): Instant = fixedInstant
        })
    }

    @AfterTest
    fun resetClock() {
        DelegatedClock.reset()
    }


    @Test
    fun `should display 'today'`() {
        val result = Instant.parse("2025-01-20T16:50:19.730740Z").getDisplayableDayCount()
            .also { println(it) }
        assertEquals(expected = "today", actual = result)
    }

    @Test
    fun `should display prior date`() {
        val result = Instant.parse("2025-01-14T09:49:19.730740Z").getDisplayableDayCount()
            .also { println(it) }
        assertEquals(expected = "6 days ago", actual = result)
    }

    @Test
    fun `should display posterior date`() {
        val result = Instant.parse("2025-01-28T18:51:19.730740Z").getDisplayableDayCount()
            .also { println(it) }
        assertEquals(expected = "in 8 days", actual = result)
    }
}