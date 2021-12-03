fun main() {
    fun part1(input: List<Int>): Int {
        return input
            .zipWithNext()
            .count { (a, b) -> b > a }
    }

    fun part2(input: List<Int>): Int {
        return input
            .windowed(3) { it.sum() }
            .zipWithNext()
            .count { (a, b) -> b > a }
    }

    val testInput = readInputAsInts("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInputAsInts("Day01")
    println(part1(input))
    println(part2(input))
}
