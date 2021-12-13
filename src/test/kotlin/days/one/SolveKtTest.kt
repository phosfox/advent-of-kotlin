package days.one

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SolveKtTest {

    private val input = listOf(
        199,
        200,
        208,
        210,
        200,
        207,
        240,
        269,
        260,
        263
    )

    @Test
    fun countIncreases() {
        assertEquals(days._01.countIncreases(input), 7)
    }
}