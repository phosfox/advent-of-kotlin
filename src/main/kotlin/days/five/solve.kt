package days.five

import java.io.File

data class Coord(val x: Int, val y: Int)

val raw = listOf(
    "0,9 -> 5,9",
    "8,0 -> 0,8",
    "9,4 -> 3,4",
    "2,2 -> 2,1",
    "7,0 -> 7,4",
    "6,4 -> 2,0",
    "0,9 -> 2,9",
    "3,4 -> 1,4",
    "0,0 -> 8,8",
    "5,5 -> 8,2"
)

fun toCoord(s: String): Coord {
    val (x,y) = s.split(",").map { it.toInt() }
    return Coord(x,y)
}

fun toLines(ps: Pair<Coord, Coord>): List<Coord> {
    val (left,right) = ps
    val (xStart, xEnd) = arrayOf(left.x, right.x).sortedArray()
    val (yStart, yEnd) = arrayOf(left.y, right.y).sortedArray()
    val lines = arrayListOf<Coord>()
    for (xi in xStart..xEnd) {
        for(yi in yStart..yEnd) {
            lines.add(Coord(xi, yi))
        }
    }
    return lines
}

fun toDiagonales(ps: Pair<Coord, Coord>): List<Coord> {
    val (left, right) = ps
    val xRange = if (left.x < right.x) left.x.rangeTo(right.x) else left.x.downTo(right.x)
    val yRange = if (left.y < right.y) left.y.rangeTo(right.y) else left.y.downTo(right.y)
    return xRange.zip(yRange).map { Coord(it.first, it.second) }
}

fun parseLine(s: String): Pair<Coord, Coord> {
    val (start,end) = "(\\d+,\\d+) -> (\\d+,\\d+)".toRegex().find(s)!!.destructured
    return Pair(toCoord(start), toCoord(end))
}



fun part1() {
    val coordPairs= File("src/main/kotlin/days/five/input.txt").readLines().map { parseLine(it) }
    val filtered = coordPairs.filter { (left, right) -> (left.x == right.x) or (left.y == right.y) }
    val lines = filtered.map { toLines(it) }
    val counts = lines.flatten().groupingBy { it }.eachCount().filterValues { it >= 2 }.size
    println(counts)
}
fun part2() {
    val coordPairs= File("src/main/kotlin/days/five/input.txt").readLines().map { parseLine(it) }
    val filtered = coordPairs.filter { (left, right) -> (left.x == right.x) or (left.y == right.y) }
    val notFiltered = coordPairs.filterNot { (left, right) -> (left.x == right.x) or (left.y == right.y) }
    val lines = filtered.map { toLines(it) }
    val diags = notFiltered.map { toDiagonales(it) }
    val combined = lines + diags
    val counts = combined.flatten().groupingBy { it }.eachCount().filterValues { it >= 2 }.size
    println(counts)
}
