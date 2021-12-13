package days.thirteen

import utils.Grid
import utils.Point
import utils.parse


fun solve() {
    val (dots, rawInstructions) = parse("src/main/kotlin/days/thirteen/input.txt", { it }, sep = "\n\n")
    var grid: Grid<String> = Grid(dots.split("\n").associate {
        val (x, y) = it.split(",")
        Point(x.toInt(), y.toInt()) to "#"
    })
    val instructions = rawInstructions.split("\n").map {
        val (xy, d) = "([xy])=(\\d+)".toRegex().find(it)!!.destructured
        xy to d.toInt()
    }
    val (axis, num) = instructions.first()
    println("Part 1: ${grid.foldAt(axis, num).size}")
    instructions.forEach{
            (axis, num) ->
        grid = grid.foldAt(axis,num)
    }
    grid.print()
}