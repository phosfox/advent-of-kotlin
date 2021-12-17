package days._17

import javax.lang.model.element.VariableElement
import kotlin.system.measureTimeMillis


data class TargetRange(val x: IntRange, val y: IntRange)


//target area: x=20..30, y=-10..-5
val testTarget = TargetRange((20..30), (-10..-5))

//target area: x=265..287, y=-103..-58
val target = TargetRange((265..287), (-103..-58))

data class Probe(val x: Int, val y: Int, val xVel: Int, val yVel: Int)

fun step(p: Probe): Probe {
    val (x, y, xV, yV) = p
    val newXV: Int = when {
        xV > 0 -> xV - 1
        xV < 0 -> xV + 1
        else -> 0
    }
    return Probe(x + xV, y + yV, newXV, yV - 1)
}

fun isWithin(probe: Probe, range: TargetRange): Boolean {
    val (x, y, _, _) = probe
    return x in range.x && y in range.y
}

fun willHit(probe: Probe, target: TargetRange): Boolean {
    var p = probe;
    while (true) {
        p = step(p)
        if (isWithin(p, target)) return true
        if (p.y < target.y.first) return false
    }
}

fun slowHighestPoint(probe: Probe): Int {
    val steps = generateSequence(probe) { step(it) }.dropWhile { it.yVel != 0 }.take(1).toList().first()
    return steps.y
}

fun highestPoint(probe: Probe): Int {
    var newY = probe.y
    for (vy in probe.yVel downTo 1) {
        newY += vy;
    }
    return newY
}

fun part1() {
    var highestY = 0;
    var highestYV = 0;
    for (xV in 0..1000) {
        for (yV in 0..1000) {
            val start = Probe(0, 0, xV, yV)
            if (willHit(start, target) && highestPoint(start) > highestY) {
                highestY = highestPoint(start)
                highestYV = yV;
            }
        }
    }
    println("Highest Y: $highestY, highestYV: $highestYV")
}

fun part2() {
    val t = target
    val velocities = mutableSetOf<Probe>()
    for (xV in 0..1000) {
        for (yV in (t.y.first)..1000) {
            val start = Probe(0, 0, xV, yV)
            if (willHit(start, t) ) {
                velocities.add(start)
            }
        }
    }
    println(velocities.size)

}

fun solve() {
    //part1()
    part2()

}

