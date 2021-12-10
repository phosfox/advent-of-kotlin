package days.ten

import java.io.File
import java.util.ArrayDeque

val testInput = File("src/main/kotlin/days/ten/test-input.txt").readLines()
val input = File("src/main/kotlin/days/ten/input.txt").readLines()

val pairs = mapOf(
    '(' to ')', '{' to '}', '[' to ']', '<' to '>', ')' to '(', '}' to '{', ']' to '[', '>' to '<'
)


fun matching(left: Char, right: Char) = pairs[left] == right

val opening = setOf('(', '{', '[', '<')
val closing = setOf(')', '}', ']', '>')

fun score(line: String): Int {
    val scores = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
    val stack = ArrayDeque<Char>()
    line.forEach {
        if (it in opening) {
            stack.push(it)
        }
        if (it in closing) {
            val ele = stack.pop() ?: return 0
            if (!matching(it, ele)) {
                return scores.getOrDefault(it, 0)
            }
        }
    }
    return 0
}

fun closingChars(line: String): ArrayDeque<Char> {
    val stack = ArrayDeque<Char>()
    line.forEach {
        if (it in opening) {
            stack.push(it)
        }
        if (it in closing) {
            val ele = stack.peek() ?: return stack
            if (!matching(it, ele)) {
                return stack
            }
           stack.pop()
        }
    }
    return stack
}

fun completionScore(ls: List<Char>): Long {
    val scores = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)
    return ls.fold(0) { acc, c -> (acc * 5) + scores.getOrDefault(c, 0) }
}

fun part1() {
    println(input.sumOf { score(it) })
}

fun part2() {
    val incomplete = input.filter { score(it) == 0 }
    val res = incomplete.map { it -> closingChars(it).map { pairs[it]!! } }.map { completionScore(it) }
    val winner = res.sorted()[res.size / 2]
    println(winner)
}