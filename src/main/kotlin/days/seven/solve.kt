package days.seven

import java.io.File

fun fuelCost(from: Int, to: Int): Int {
    return kotlin.math.abs(from - to)
}
fun increasedFuelCost(from: Int, to: Int): Int {
    val n = kotlin.math.abs(from - to)
    return (n*(n+1))/2
}

val input = File("src/main/kotlin/days/seven/input.txt").readText()

fun part1() {
    val positions = input.split(",").map(String::toInt)
    val possiblePositions = positions.let { list -> list.minOrNull()?.rangeTo(list.maxOrNull()!!)?.toList() }
    val fuelCosts = possiblePositions?.map { from -> positions.sumOf { to -> fuelCost(from, to) } }
    println(fuelCosts?.minOrNull())
}

fun part2() {
    val positions = input.split(",").map(String::toInt)
    val possiblePositions = positions.let { list -> list.minOrNull()?.rangeTo(list.maxOrNull()!!)?.toList() }
    val fuelCosts = possiblePositions?.map { from -> positions.sumOf { to -> increasedFuelCost(from, to) } }
    println(fuelCosts?.minOrNull())
}
