import kotlin.math.abs

private data class Point(val x: Int, val y: Int)
private data class Vector(val start: Point, val end: Point)

private val vectorRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

private fun String.toVector() = vectorRegex.matchEntire(this)?.let {
    val (x1, y1, x2, y2) = it.destructured
    Vector(Point(x1.toInt(), y1.toInt()), Point(x2.toInt(), y2.toInt()))
} ?: error("Couldn't parse vector: $this")

private fun Vector.part1Points() = when {
    start.x == end.x -> verticalPoints()
    start.y == end.y -> horizontalPoints()
    else -> emptySequence()
}

private fun Vector.part2Points() = when {
    start.x == end.x -> verticalPoints()
    start.y == end.y -> horizontalPoints()
    abs(start.x - end.x) == abs(start.y - end.y) -> diagonalPoints()
    else -> emptySequence()
}

private infix fun Int.coordinatesTo(end: Int) =
    IntProgression.fromClosedRange(this, end, if (this < end) 1 else -1)

private fun Vector.horizontalPoints() =
    (start.x coordinatesTo end.x).asSequence().map { start.copy(x = it) }

private fun Vector.verticalPoints() =
    (start.y coordinatesTo end.y).asSequence().map { start.copy(y = it) }

private fun Vector.diagonalPoints() =
    (start.x coordinatesTo end.x).asSequence().mapIndexed { index, x ->
        val y = if (start.y < end.y) start.y + index else start.y - index
        Point(x, y)
    }

fun main() {
    fun part1(input: List<String>): Int = input
        .asSequence()
        .map { it.toVector() }
        .flatMap { it.part1Points() }
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }

    fun part2(input: List<String>): Int = input
        .asSequence()
        .map { it.toVector() }
        .flatMap { it.part2Points() }
        .groupingBy { it }
        .eachCount()
        .count { it.value >= 2 }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
