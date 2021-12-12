package days.twelve

import utils.parse
import java.util.*
import kotlin.collections.ArrayDeque

//procedure DFS(G, v) is
//label v as discovered
//for all directed edges from v to w that are in G.adjacentEdges(v) do
//if vertex w is not labeled as discovered then
//recursively call DFS(G, w)

typealias Graph = Map<String, Set<String>>

fun isSmallCave(s: String) = s.first().isLowerCase()

fun dfs(graph: Graph, start: String): MutableList<List<String>> {
    val paths = mutableListOf<List<String>>()
    fun helper(start: String, path: List<String>) {
        for(w in graph[start]!!) {
            if (isSmallCave(w) && path.contains(w) || w == "start") continue
            if (w == "end") {
                paths.add(path.plus("end"))
                continue
            }
            helper(w, path.plus(w))
        }
    }
    helper(start, listOf())
    return paths
}

private fun part1() {
    val graph = parse("src/main/kotlin/days/twelve/input.txt", {
        val (k, v) = it.split("-")
        listOf(k to v, v to k)
    }).flatten().groupBy({ it.first }, { it.second }).mapValues { it.value.toSet() }
    println(graph)
    val paths = dfs(graph, "start")
    println(paths.size)

}

fun solve() {
    part1()
}