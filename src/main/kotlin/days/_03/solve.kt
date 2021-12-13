package days._03

import java.io.File

typealias Grid<T> = List<List<T>>

fun <T> Grid<T>.column(index: Int): List<T> {
    return this.map { it[index] }
}

val binaries =
    File("src/main/kotlin/days/_03/input.txt").readLines().map { outerIt -> outerIt.map { it.digitToInt() } }



fun mostFrequent(ls: List<List<Int>>, colIndex: Int): Int? {
    return ls.column(colIndex).groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
}

fun leastFrequent(ls: List<List<Int>>, colIndex: Int): Int? {
    return ls.column(colIndex).groupingBy { it }.eachCount().minByOrNull { it.value }?.key
}

fun frequencies(ls: List<Int>): Map<Int, Int> {
   return ls.groupingBy { it }.eachCount()
}

fun rate(ls: List<List<Int>>, freq: (ls: List<List<Int>>, colIndex: Int) -> Int?): Int {
    val freqs = arrayListOf<Int?>()
    for (i in 0 until ls.first().size) {
        freqs.add(freq(ls, i))
    }
    return freqs.joinToString("").toInt(2)
}

fun oxyFilterCriterium(ls: List<Int>): Int {
    val freqs = frequencies(ls)
    val mostCommon = freqs.maxByOrNull { it.value }
    val leastCommon = freqs.minByOrNull { it.value }
    return  if (mostCommon?.value == leastCommon?.value) 1 else mostCommon?.key!!
}

fun co2FilterCriterium(ls: List<Int>): Int {
    val freqs = frequencies(ls)
    val mostCommon = freqs.maxByOrNull { it.value }
    val leastCommon = freqs.minByOrNull { it.value }
    return  if (mostCommon?.value == leastCommon?.value) 0 else leastCommon?.key!!
}

fun rating(ls: List<List<Int>>, criteria: (ls: List<Int>) -> Int): List<Int> {
    var ret = ls
    for(i in ls.first().indices) {
        if (ret.size == 1) return ret.flatten()
        val pred = criteria(ret.column(i))
        ret = ret.filter { it[i] == pred}
    }
    return ret.flatten()
}

fun part1() {
    val gammaRate = rate(binaries, ::mostFrequent)
    val epsilonRate = rate(binaries, ::leastFrequent)
    println(gammaRate * epsilonRate)
}

fun part2() {
    val oxy = rating(binaries, ::oxyFilterCriterium).joinToString("").toInt(2)
    val co2 = rating(binaries, ::co2FilterCriterium).joinToString("").toInt(2)
    println(oxy * co2)
}