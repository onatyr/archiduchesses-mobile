package fr.onat.turboplant.libs.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class NumberExts {

    @Test
    fun `should display value with singular unit`() {
        val result = (1L).toStringWithUnit("day", "days")
        assertEquals(expected = "1 day", actual = result)
    }

    @Test
    fun `should display value with plural unit`() {
        val result = (2L).toStringWithUnit("day", "days")
        assertEquals(expected = "2 days", actual = result)
    }

    @Test
    fun `should throw IllegalArgumentException`() {
        assertFailsWith<IllegalArgumentException> {
            (-1L).toStringWithUnit("day", "days")
        }
    }
}