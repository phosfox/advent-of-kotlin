package days.one

import java.io.File

fun countIncreases (ls: List<Int>): Int {
    return ls.zipWithNext().filter { pair -> pair.first < pair.second }.size
}

private val depths = File("src/main/kotlin/days/one/input.txt").readLines().map {it.toInt()}

fun part1 () {
    val increases = countIncreases(depths)
    println("Part 1: $increases")
}

fun part2 () {
    val sums = depths.windowed(3).map { it.sum() }
    val increases = countIncreases(sums)
    println("Part 2: $increases")
}
