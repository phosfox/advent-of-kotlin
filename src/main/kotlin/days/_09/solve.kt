package days._09

import java.io.File
import java.util.*

fun neighbours(grid: List<List<Int>>, x: Int, y: Int): List<Int> {
    val up = grid.getOrNull(x)?.getOrNull(y - 1)
    val down = grid.getOrNull(x)?.getOrNull(y + 1)
    val left = grid.getOrNull(x - 1)?.getOrNull(y)
    val right = grid.getOrNull(x + 1)?.getOrNull(y)
    return listOfNotNull(up, down, left, right)
}

fun isLowPoint(height: Int, nbs: List<Int>): Boolean {
    return nbs.all { nb -> nb > height }
}


fun part1() {
    val testInput = File("src/main/kotlin/days/_09/input.txt").readLines()
    val grid = testInput.map { line -> line.trim().chunked(1).map(String::toInt) }
    val lowPoints = arrayListOf<Int>()
    grid.forEachIndexed { x, row ->
        row.forEachIndexed { y, height ->
            if (isLowPoint(height, neighbours(grid, x, y))) lowPoints.add(height)
        }
    }
    val riskLevels = lowPoints.map { it.inc() }
    println(riskLevels.sum())
}


//1  procedure BFS(G, root) is
//2      let Q be a queue
//3      label root as explored
//4      Q.enqueue(root)
//5      while Q is not empty do
//6          v := Q.dequeue()
//7          if v is the goal then
//8              return v
//9          for all edges from v to w in G.adjacentEdges(v) do
//10              if w is not labeled as explored then
//11                  label w as explored
//12                  Q.enqueue(w)

fun adjacents(grid: List<List<Int>>, root: Pair<Int, Int>): List<Pair<Int, Int>> {
    val (x, y) = root
    val nbs = listOf(Pair(x + 1, y), Pair(x - 1, y), Pair(x, y + 1), Pair(x, y - 1))
    return nbs.filter { (x, y) ->
        (x >= 0 && x < grid.size) &&
                (y >= 0 && y < grid.first().size)
                && (grid[x][y] != 9)
    }
}

fun basin(grid: List<List<Int>>, root: Pair<Int, Int>): Int {
    val explored = mutableSetOf(root)
    val q: Queue<Pair<Int, Int>> = LinkedList(listOf(root))
    while (q.isNotEmpty()) {
        val v = q.poll() ?: break
        adjacents(grid, v).forEach { w ->
            if (!explored.contains(w)) {
                explored.add(w)
                q.add(w)
            }
        }
    }
    return explored.size
}

fun part2() {
    val testInput = File("src/main/kotlin/days/_09/input.txt").readLines()
    val grid = testInput.map { line -> line.trim().chunked(1).map(String::toInt) }
    val lowPoints = arrayListOf<Pair<Int,Int>>()
    grid.forEachIndexed { x, row ->
        row.forEachIndexed { y, height ->
            if (isLowPoint(height, neighbours(grid, x, y))) lowPoints.add(Pair(x,y))
        }
    }
    val basinSizes = lowPoints.map { basin(grid, it) }
    println(basinSizes.sorted().takeLast(3).reduce{prod, ele -> prod * ele})
}
