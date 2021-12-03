package days.three

import java.io.File

val binaries = File("src/main/kotlin/days/three/input.txt").readLines().map { outerIt -> outerIt.map { it.digitToInt() } }

fun mostFrequent(ls: List<Int>): Int? {
    return ls.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
}

fun leastFrequent(ls: List<Int>): Int? {
    return ls.groupingBy { it }.eachCount().minByOrNull { it.value }?.key
}


fun calcGammaRate (ls: List<List<Int>>): Int {
    val mostFreqs = arrayListOf<Int?>()
    for (i in 0 until ls.first().size) {
        val ss = ls.map { it[i] }
        mostFreqs.add(mostFrequent(ss))
    }
    return mostFreqs.joinToString("").toInt(2)
}
fun calcEpsilonRate (ls: List<List<Int>>): Int {
    val leastFreq = arrayListOf<Int?>()
    for (i in 0 until ls.first().size) {
        val ss = ls.map { it[i] }
        leastFreq.add(leastFrequent(ss))
    }
    return leastFreq.joinToString("").toInt(2)
}


fun part1() {
    val gammaRate = calcGammaRate(binaries)
    val epsilonRate = calcEpsilonRate(binaries)
    println(gammaRate * epsilonRate)
}