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

fun temp(ls: List<List<Int>>, colIndex: Int): Int {
    val counts = column(ls, colIndex).groupingBy { it }.eachCount()
    val zero = Pair(0, counts.getValue(0))
    val one = Pair(1, counts.getValue(1))
    return if (zero.second >= one.second) zero.first else one.first
}

fun leastFrequent(ls: List<List<Int>>, colIndex: Int): Int? {
    return column(ls, colIndex).groupingBy { it }.eachCount().minByOrNull { it.value }?.key
}

fun frequencies(ls: List<Int>): Map<Int, Int> {
   return ls.groupingBy { it }.eachCount()
}

fun calcGammaRate(ls: List<List<Int>>): Int {
    val mostFreqs = arrayListOf<Int?>()
    for (i in 0 until ls.first().size) {
        mostFreqs.add(mostFrequent(ls, i))
    }
    return mostFreqs.joinToString("").toInt(2)
}

fun calcEpsilonRate(ls: List<List<Int>>): Int {
    val leastFreq = arrayListOf<Int?>()
    for (i in 0 until ls.first().size) {
        leastFreq.add(leastFrequent(ls, i))
    }
    return leastFreq.joinToString("").toInt(2)
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

fun oxygenRating(ls: List<List<Int>>): List<Int> {
    var ret = ls
    for(i in ls.first().indices) {
        if (ret.size == 1) return ret.flatten()
        val pred = oxyFilterCriterium(column(ret, i))
         ret = ret.filter { it[i] == pred}
    }
    return ret.flatten()
}

fun co2Rating(ls: List<List<Int>>): List<Int> {
    var ret = ls
    for(i in ls.first().indices) {
        if (ret.size == 1) return ret.flatten()
        val pred = co2FilterCriterium(column(ret, i))
        ret = ret.filter { it[i] == pred}
    }
    return ret.flatten()
}

fun part1() {
    val gammaRate = calcGammaRate(binaries)
    val epsilonRate = calcEpsilonRate(binaries)
    println(gammaRate * epsilonRate)
}

fun part2() {
    val oxy = oxygenRating(binaries).joinToString("").toInt(2)
    val co2 = co2Rating(binaries).joinToString("").toInt(2)
    println(oxy * co2)
}