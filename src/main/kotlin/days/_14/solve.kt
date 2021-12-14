package days._14

import utils.parse

fun pairInsertion(pair: String, rules: Map<String, String>): StringBuilder {
    val between = rules.getOrDefault(pair, "")
    return StringBuilder(pair.first() + between + pair.last())
}

fun solve() {
    val (template, rawInsertions) = parse("src/main/kotlin/days/_14/input.txt", { it }, sep = "\n\n")
    val insertionRules = rawInsertions.split("\n").associate {
        val (l, r) = it.split(" -> ")
        l to r
    }
    var templateBuilder = StringBuilder(template)
    println(templateBuilder.windowed(2))
    for (i in 1..40) {
        templateBuilder =
            templateBuilder.windowed(2).map { pairInsertion(it, insertionRules) }.reduce { a,b -> a.append(b.drop(1))}
    }
    val grouped = templateBuilder.groupingBy { it }.eachCount()
    val mostCommon = grouped.maxOf { it.value }
    val leastCommon = grouped.minOf { it.value }
    println(mostCommon)
    println(leastCommon)
    println(mostCommon - leastCommon)

}