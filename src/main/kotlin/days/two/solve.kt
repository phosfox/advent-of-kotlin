package days.two

import java.io.File

typealias Instruction = Pair<String, Int>;

private val lines = File("src/main/kotlin/days/two/input.txt").readLines()

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
