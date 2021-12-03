package days.three

import java.io.File

val test = listOf(
    "00100",
    "11110",
    "10110",
    "10111",
    "10101",
    "01111",
    "00111",
    "11100",
    "10000",
    "11001",
    "00010",
    "01010"
).map { outer -> outer.map { it.digitToInt() } }


val binaries =
    File("src/main/kotlin/days/three/input.txt").readLines().map { outerIt -> outerIt.map { it.digitToInt() } }

fun column(ls: List<List<Int>>, idx: Int): List<Int> {
    return ls.map { it[idx] }
}

fun mostFrequent(ls: List<List<Int>>, colIndex: Int): Int? {
    return column(ls, colIndex).groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
}

fun leastFrequent(ls: List<List<Int>>, colIndex: Int): Int? {
    return column(ls, colIndex).groupingBy { it }.eachCount().minByOrNull { it.value }?.key
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
        val pred = criteria(column(ret, i))
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