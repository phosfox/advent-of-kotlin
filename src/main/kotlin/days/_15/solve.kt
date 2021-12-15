package days._15

import utils.Grid
import utils.Point
import utils.ints
import utils.parse
import java.lang.Double.POSITIVE_INFINITY
import java.util.*


fun dijkstra(g: Grid<Int>, source: Point): Pair<MutableMap<Point, Int>, MutableMap<Point, Point?>> {
    val distances: MutableMap<Point, Int> = mutableMapOf()
    val previous: MutableMap<Point, Point?> = mutableMapOf()
    distances[source] = 0
    data class Node(val p: Point, val risk: Int)

    val compByRisk: Comparator<Node> = compareBy { it.risk }
    val q: PriorityQueue<Node> = PriorityQueue<Node>(compByRisk)

    g.map.keys.forEach {
        if (it != source) {
            distances[it] = 100000
            previous[it] = null

        }
        q.add(Node(it, distances.getOrDefault(it, 10000)))
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

fun solve() {
    val input = parse("src/main/kotlin/days/_15/input.txt", { line -> line.chunked(1).map { it.toInt() } })
    val m = mutableMapOf<Point, Int>()
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
    val risk = path.sumOf { g.map[it]!! }
    println(risk - 1)
}

