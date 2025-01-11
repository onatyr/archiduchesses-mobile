package fr.onat.turboplant.libs.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NumberExts {

    @Test
    fun `should display value with singular unit`() {
        assertEquals(
            expected = "1 day",
            actual = (1L).toStringWithUnit("day", "days")
        )
    }

    @Test
    fun `should display value with plural unit`() {
        assertEquals(
            expected = "2 days",
            actual = (2L).toStringWithUnit("day", "days")
        )
    }

    @Test
    fun `should throw IllegalArgumentException`() {
        assertFailsWith<IllegalArgumentException> {
            (-1L).toStringWithUnit("day", "days")
        }
    }
}