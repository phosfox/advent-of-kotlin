package days._15

import utils.Grid
import utils.Point
import utils.parse
import java.lang.Double.POSITIVE_INFINITY
import java.util.*


fun dijkstra(g: Grid<Long>, source: Point): Pair<MutableMap<Point, Long>, MutableMap<Point, Point>> {
    val distances: MutableMap<Point, Long> = g.map.keys.associateWith { 100000L }.toMutableMap()
    val previous: MutableMap<Point, Point> = mutableMapOf()
    distances[source] = g.map.getOrDefault(source, 9L)
    data class Node(val p: Point, val risk: Long)

    val compByRisk: Comparator<Node> = compareBy { it.risk }
    val q: PriorityQueue<Node> = PriorityQueue<Node>(compByRisk)

    g.map.keys.forEach {
        q.add(Node(it, distances.getOrDefault(it, POSITIVE_INFINITY.toLong())))
    }

    while (q.isNotEmpty()) {
        val (u, _) = q.poll()
        g.neighbours(u).forEach { v ->
            val alt = distances[u]!! + g.map[v]!!
            if (alt < distances[v]!!) {
                distances[v] = alt
                previous[v] = u
                q.add(Node(v, alt))
            }
        }
    }

    return Pair(distances, previous)

}

fun shortestPath(to: Point, previous: Map<Point, Point?>): MutableList<Point> {
    val s = mutableListOf<Point>()
    var u: Point? = to
    while (true) {
        if (u == null) return s
        s.add(u)
        u = previous[u]
    }
}

fun wrapAdd(l: Long, add: Long): Long {
    var ret = l
    for (i in 0 until add) {
        ret = (ret + 1) % 10
        if (ret == 0L) {
            ret = 1L
        }
    }
    return ret
}

fun mapInc(ls: List<Long>): List<Long> {
    return (0..4L).map { num ->
        ls.map {
            wrapAdd(it, num)
        }
    }.flatten()
}

fun gridInc(grid: List<List<Long>>): List<List<Long>> {
    val incGrid =
        (0..4L).map { num -> grid.map { mapInc(it) }.flatten().map { wrapAdd(it, num) }.chunked(grid.first().size * 5) }
    return incGrid.flatten()
}

val input = parse("src/main/kotlin/days/_15/input.txt", { line -> line.chunked(1).map { it.toLong() } })

fun solve() {
    val m = mutableMapOf<Point, Long>()
    input.forEachIndexed { y, row ->
        row.forEachIndexed { x, risk ->
            m[Point(x, y)] = risk
        }
    }
    val g = Grid(m)
    val start = Point(0, 0)
    val target = Point(g.width, g.height)
    val (dist, prev) = dijkstra(g, start)
    val path = shortestPath(target, prev)
    val risk = path.dropLast(1).sumOf { g.map[it] ?: 0 }
    println(risk)


    part2()


}

fun part2() {

    val hugeMap = mutableMapOf<Point, Long>()
    val hugeInput = gridInc(input)
    hugeInput.forEachIndexed { y, row ->
        row.forEachIndexed { x, risk ->
            hugeMap[Point(x, y)] = risk
        }
    }
    val hugeG = Grid(hugeMap)
    val hugeStart = Point(0, 0)
    val hugeTarget = Point(hugeG.width, hugeG.height)
    val (hugeDist, hugePrev) = dijkstra(hugeG, hugeStart)
    val hugePath = shortestPath(hugeTarget, hugePrev)
    val hugeRisk = hugePath.dropLast(1).sumOf { hugeG.map[it] ?: 0 }
    println(hugeRisk)
}
