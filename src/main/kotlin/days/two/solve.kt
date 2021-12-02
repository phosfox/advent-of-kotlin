package days.two

import java.io.File

typealias Instruction =  Pair<String, Int>;

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
    val commands = parseInstructions(lines)
    commands.forEach {
        val (operation, num) = it
        when(operation) {
            "forward" -> horPos += num
            "down" -> depths += num
            "up" -> depths -= num
        }
    }

    println(depths * horPos)
}
