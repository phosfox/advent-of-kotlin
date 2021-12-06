package days.six

import java.io.File

class Lanternfish(var age: Int) {
    fun tick(): Lanternfish? {
        if (this.age <= 0) {
            this.age = 6;
            return Lanternfish(8)
        }
        this.age--
        return null
    }
}



fun part1() {
    val init = File("src/main/kotlin/days/six/input.txt").readText().split(",").map(String::toInt)
    val fishes = ArrayList(init.map { Lanternfish(it)})
    for (i in 1..256) {
        val tempFishes = arrayListOf<Lanternfish>()
        fishes.forEach { parent ->
            val maybeChild = parent.tick()
            maybeChild?.let { tempFishes.add(it) }
        }
        fishes.addAll(tempFishes)
    }
    println(fishes.size)
}

fun step(counts: Array<Long>): Array<Long> {
    val result = arrayOf<Long>(0,0,0,0,0,0,0,0,0)
    result[6] = counts[0]
    result[8] = counts[0]
    for (i in 0 until 8) {
        result[i] += counts[i+1]
    }
    return result
}

fun run(counts: Array<Long>, n: Int): Long {
    var result = counts
    for (i in 0 until n) {
         result = step(result)
    }
    return result.sum()
}

fun part2() {
    val input = File("src/main/kotlin/days/six/input.txt").readText()
    val counts = (0..8).map { num -> input.count { num.toString() == it.toString()} }.map { it.toLong() }
    println(run(arrayOf<Long>().plus(counts),256))

}