package days._01

import utils.parse
import java.io.File

fun countIncreases(ls: List<Int>): Int {
    return ls.zipWithNext().filter { (first, second) -> first < second }.size
}

private val depths = parse("src/main/kotlin/days/_01/input.txt", {it.toInt()})

fun part1() {
    val increases = countIncreases(depths)
    println("Part 1: $increases")
}

fun part2() {
    val sums = depths.windowed(3).map { it.sum() }
    val increases = countIncreases(sums)
    println("Part 2: $increases")
}
