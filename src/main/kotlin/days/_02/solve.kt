package days._02

import java.io.File

typealias Instruction = Pair<String, Int>;

private val lines = File("src/main/kotlin/days/_02/input.txt").readLines()

fun parseInstructions(ls: List<String>): List<Instruction> {
    val ret = ls.map {
        val (cmd, num) = it.split(" ")
        Pair(cmd, num.toInt())
    }
    return ret;
}

fun part1() {
    var depths = 0;
    var horPos = 0;
    val instructions = parseInstructions(lines)
    instructions.forEach {
        val (operation, num) = it
        when (operation) {
            "forward" -> horPos += num
            "down" -> depths += num
            "up" -> depths -= num
        }
    }

    println(depths * horPos)
}

fun part15() {
    data class Position(var h: Int, var d: Int)

    parseInstructions(lines)
        .fold(
            Position(0, 0)
        ) { acc, (operation, num) ->
            when (operation) {
                "forward" -> acc.apply { h += num }
                "down" -> acc.apply { d += num }
                "up" -> acc.apply { d -= num }
                else -> acc
            }
        }.let {
            println(it.h * it.d)

        }
}


fun part2() {
    var depths = 0;
    var horPos = 0;
    var aim = 0
    val instructions = parseInstructions(lines)
    instructions.forEach {
        val (operation, num) = it
        when (operation) {
            "forward" -> {
                horPos += num
                depths += aim * num;
            }
            "down" -> aim += num
            "up" -> aim -= num
        }
    }

    println(depths * horPos)
}
