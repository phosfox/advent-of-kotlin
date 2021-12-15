package utils

import java.io.File
import java.util.*
import kotlin.Int.Companion.MAX_VALUE
import kotlin.math.abs

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

class Grid<T>(val map: Map<Point, T>) {

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

    fun foldAt(axis: String, num: Int): Grid<T> {
        return if (axis == "x") {
            foldXAxis(num)
        } else {
            foldYAxis(num)
        }
    }

    private fun foldYAxis(num: Int): Grid<T> {
        val pointsBelow = this.map.entries.filter { (k, _) -> k.y > num }.associate { (k, v) -> k to v }
        val pointsAbove = this.map.entries.filter { (k, _) -> k.y < num }.associate { (k, v) -> k to v }
        val translatedBelows = pointsBelow.mapKeys { (p, k) -> Point(p.x, num - abs(p.y - num)) }
        return Grid(
            (translatedBelows.asSequence() + pointsAbove.asSequence()).distinct().associate { (k, v) -> k to v })
    }

    private fun foldXAxis(num: Int): Grid<T> {
        val pointsBelow = this.map.entries.filter { (k, _) -> k.x > num }.associate { (k, v) -> k to v }
        val pointsAbove = this.map.entries.filter { (k, _) -> k.x < num }.associate { (k, v) -> k to v }
        val translatedBelows = pointsBelow.mapKeys { (p, k) -> Point(num - abs(p.x - num), p.y) }
        return Grid(
            (translatedBelows.asSequence() + pointsAbove.asSequence()).distinct().associate { (k, v) -> k to v })
    }

    val size: Int
        get() = map.size

    fun print() {
        for (y in 0..height) {
            for (x in 0..width) {
                print(map.getOrDefault(Point(x, y), "."))
            }
            println()
        }
    }

}

/*


77777777777777777

(0,12)

12-7 = 5
new spot = (0,7-5)
 */