package days.eight

import java.io.File

val testInput =
    File("src/main/kotlin/days/eight/test-input.txt").readLines().map { it.split(" | ").last().trim().split(" ") }
        .flatten()
val input =
    File("src/main/kotlin/days/eight/input.txt").readLines().map { it.split(" | ").last().trim().split(" ") }.flatten()
//Number:Segments
//1:2,4:3,7:4,8:7


fun part1() {
    val numberOfSegments = arrayListOf(2, 3, 4, 7)
    val counts = input.groupingBy { it.length }.eachCount()
    println(numberOfSegments.sumOf { num -> counts.getValue(num) })
}

fun set(signal: List<String>, len: Int) = signal.find { it.length == len }!!.toSet()

fun toNumber(signal: List<String>, output: List<Set<Char>>): Int {
    val segments = setOf('a','b','c','d','e','f','g')
    val counts = signal.joinToString(separator = "").groupingBy { it }.eachCount()
    val one = set(signal,2)
    val four = set(signal, 4)
    val seven = set(signal, 3)
    val eight = set(signal, 7)
    val bottomRight = counts.filterValues { it == 9 }.keys
    val left = counts.filterValues { it == 6 }.keys
    val bottomLeft = counts.filterValues { it == 4 }.keys
    val top = seven.minus(one)
    val bottom = eight.minus(four.plus(top).plus(bottomLeft))
    val mid = four.minus(one.plus(left))
    val right = segments.minus(bottom.plus(bottomLeft).plus(bottomRight).plus(left).plus(top).plus(mid))
    val zero = eight.minus(mid)
    val six = eight.minus(right)
    val five = six.minus(bottomLeft)
    val nine = eight.minus(bottomLeft)
    val three = nine.minus(left)
    val two = eight.minus(left).minus(bottomRight)
    val digits = arrayListOf(zero,one,two,three,four,five,six,seven,eight,nine)
    val result = arrayListOf<Int>()
    output.forEach {  o ->
        digits.forEachIndexed { index, d ->
            if (o == d) result.add(index)
        }
    }
    return result.joinToString(separator = "").toInt()
}

fun part2() {
    val testInput = File("src/main/kotlin/days/eight/input.txt").readLines()
    val res = testInput.map { it.split(" | ") }.map { line -> toNumber(line.first().split(" "), line.last().split(" ").map { it.toSet() }) }
    println(res.sum())
}


/*

Top:    8
Left:   6
Right:  8
Mid:    7
BLeft:  4
BRight: 9
Bottom: 7



Top: 7 diff 1
Left:  Count = 6
Right: Last
Mid: 8 diff 0
BLeft: Count = 4
BRight: Count = 9
Bottom: (4 + top) diff 3


 */