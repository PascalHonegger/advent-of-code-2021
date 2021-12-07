import kotlin.math.abs
import kotlin.math.min

fun main() {
    /**
     * sum(1..n) = n * (n + 1) / 2
     */
    fun Int.sumNaturalNumbers() = this * (this + 1) / 2

    fun part1(input: List<Int>): Int =
        (0..input.maxOf { it }).minOf { pos -> input.sumOf { abs(it - pos) } }

    fun part2(input: List<Int>): Int =
        (0..input.maxOf { it }).minOf { pos -> input.sumOf { abs(it - pos).sumNaturalNumbers() } }

    val testInput = readWholeInputAsInts("Day07_test", ',')
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readWholeInputAsInts("Day07", ',')
    println(part1(input))
    println(part2(input))
}
