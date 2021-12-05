package days.four

import java.io.File

data class Cell(val number: Int, var marked: Boolean)

fun toBoard(ls: List<String>): List<List<Cell>> {
    return ls.map { s -> s.trim().split("\\s+".toRegex()).map { Cell(it.toInt(), false) } }
}

fun markNumber(board: List<List<Cell>>, number: Int): List<List<Cell>> {
    return board.map { row -> row.map { c -> if (c.number == number) c.apply { marked = true } else c } }
}

fun boardWins(board: List<List<Cell>>): Boolean {
    val rows = board.any { row -> row.all { it.marked } }

    val cols = (0 until board.first().size).map { column(board, it) }.any { col -> col.all { it.marked } }

    return rows.or(cols)
}

fun column(board: List<List<Cell>>, index: Int): List<Cell> {
    return board.map { it[index] }
}

fun winner(boards: List<List<List<Cell>>>, numbers: List<Int>): Pair<Int, Int> {
    var bs = boards
    for (num in numbers) {
        bs = bs.map { b -> markNumber(b, num) }
        for (i in bs.indices) {
            if (boardWins(bs[i])) {
                return Pair(i, num)
            }
        }
    }
    return Pair(-1, -1)
}

fun lastWinner(boards: List<List<List<Cell>>>, numbers: List<Int>): Pair<Int, Int> {
    var bs = boards
    val winners = arrayListOf<Int>()
    val winningNumbers = arrayListOf<Int>()
    for (num in numbers) {
        println(winners)
        if (winners.size == boards.size) return Pair(winners.last(), winningNumbers.last())
        bs = bs.map { b -> markNumber(b, num) }
        for (i in bs.indices) {
            if (boardWins(bs[i])) {
                if (!winners.contains(i)) {
                    winners.add(i)
                    winningNumbers.add(num)
                }
            }
        }
    }
    return Pair(winners.last(), winningNumbers.last())
}


val input = File("src/main/kotlin/days/four/input.txt").readLines()
val numbers = input.first().split(",").map { it.toInt() }
var boards = input.drop(1).filter { it != "" }.chunked(5).map { toBoard(it) }

fun part1() {
    val winningIndex = winner(boards, numbers)
    val score = boards[winningIndex.first].flatten().filter { !it.marked }.sumOf { it.number } * winningIndex.second
    println(score)
}

fun part2() {
    val lastWinner = lastWinner(boards, numbers)
    val score = boards[lastWinner.first].flatten().filter { !it.marked }.sumOf { it.number } * lastWinner.second
    println(score)

}

