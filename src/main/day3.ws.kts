val raw = listOf(
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
    "01010",
)

val binaries = raw.map { it.map { it.digitToInt() } }

fun mostFrequent(ls: List<Int>): Int? {
    return ls.groupingBy { it }.eachCount().maxByOrNull { it?.value }?.key
}


fun calcGammaRate (ls: List<List<Int>>): ArrayList<Int?> {
    val mostFreqs = arrayListOf<Int?>()
    for (i in 0 until binaries.first().size) {
        val ss = binaries.map { it[i] }
        mostFreqs.add(mostFrequent(ss))
    }
    return mostFreqs
}

val gammaRate = calcGammaRate(binaries)
gammaRate