package days._11

import java.io.File
import java.util.*

data class Cell(val x: Int, val y: Int)

typealias Grid = List<List<Int>>

fun Grid.print() {
    this.forEach{ println(it.toString())}
    println()
    println()
}

/*
        [x-1,y-1][x,y-1][x+1,y-1]
        [x-1,y][x,y][x+1,y]
        [x-1,y+1][x,y+1][x+1,y+1]
 */

fun adjacents(cell: Cell, grid: Grid): List<Cell> {
    val (x, y) = cell
    val topLeft = Cell(x - 1, y - 1)
    val topMid = Cell(x, y - 1)
    val topRight = Cell(x + 1, y - 1)
    val midLeft = Cell(x - 1, y)
    val midRight = Cell(x + 1, y)
    val botLeft = Cell(x - 1, y + 1)
    val botMid = Cell(x, y + 1)
    val botRight = Cell(x + 1, y + 1)
    val adjs = listOf(topLeft, topMid, topRight, midLeft, midRight, botLeft, botMid, botRight).filter { (x, y) ->
        (x >= 0 && x < grid.first().size) && (y >= 0 && y < grid.size)
    }
    return adjs
}

fun step(g: Grid): Pair<Grid, Int> {
    val stepped =
        g.asSequence().flatten().map { it.inc() % 10 }.chunked(g.size).map { it.toMutableList() }.toMutableList()
    val flashed = mutableSetOf<Cell>()
    stepped.forEachIndexed { x, row ->
        row.forEachIndexed { y, level ->
            if (level == 0) {
                flashed.add(Cell(x, y))
            }
        }
    }
    val q: Queue<Cell> = LinkedList(flashed.toList())
    while (q.isNotEmpty()) {
        val v = q.poll()
        adjacents(v, stepped).forEach { c ->
            if (c !in flashed) {
                val level = stepped[c.x][c.y]
                val newLevel = level.inc() % 10
                if (newLevel == 0) {
                    flashed.add(c)
                    q.add(c)
                }
                stepped[c.x][c.y] = newLevel
            }
        }
    }
    return Pair(stepped, flashed.size)
}


fun allFlashed(grid: Grid) = grid.flatten().all { it == 0 }



private fun part1() {
    val grid: Grid = File("src/main/kotlin/days/_11/input.txt").readLines().map { it.chunked(1) }
        .map { line -> line.map { it.toInt() } }
    val res = generateSequence(Pair(grid, 0)) { (g, flashes) ->
        val (newGrid, newFlashes) = step(g)
        Pair(newGrid, flashes + newFlashes)
    }.take(101)
    println(res.last().second)
}

private fun part2() {
    var old: Grid = File("src/main/kotlin/days/_11/input.txt").readLines().map { it.chunked(1) }
        .map { line -> line.map { it.toInt() } }
    var counter = 0
    while (!allFlashed(old)) {
        var (new, _) = step(old)
        counter +=1
        old = new.also { new = old} //funky
    }
    println(counter)
}

fun solve() {
    part1()
    part2()
}