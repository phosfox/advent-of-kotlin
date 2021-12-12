package utils

import java.io.File

fun <T> parse(path: String, parser: (s: String) -> T, sep: String = "\n"): List<T> {
    val entries = File(path).readText().trimEnd().split(sep)
    return entries.map(parser)
}

fun ints(text: String): List<Int> = "-?[0-9]+".toRegex().findAll(text).map { it.value.toInt() }.toList()
fun longs(text: String): List<Long> = "-?[0-9]+".toRegex().findAll(text).map { it.value.toLong() }.toList()

fun answer(dayNumber: Int, got: Int, expected: Int): Boolean {
    assert(got == expected) { "For $dayNumber, expected $expected but got $got" }
    return true
}

data class Point(val x: Int, val y: Int)

class Grid<T>(private val map: Map<Point, T>) {
    fun neighbours(point: Point): List<Point> {
        val (x, y) = point
        return listOf(
            Point(x + 1, y),
            Point(x - 1, y),
            Point(x, y + 1),
            Point(x, y - 1)
        ).filter { map.containsKey(it) }
    }

    fun neighbours8(point: Point): List<Point> {
        val (x, y) = point
        return listOf(
            Point(x + 1, y),
            Point(x - 1, y),
            Point(x, y + 1),
            Point(x, y - 1),
            Point(x - 1, y - 1),
            Point(x + 1, y - 1),
            Point(x - 1, y + 1),
            Point(x + 1, y + 1)
        ).filter { map.containsKey(it) }
    }

    val width: Int
        get() = map.maxOf { it.key.x }

    val height: Int
        get() = map.maxOf { it.key.y }


}