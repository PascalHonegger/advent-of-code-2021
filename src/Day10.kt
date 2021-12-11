fun main() {
    fun part1(input: List<String>): Int = input.map { row ->
        val chars = ArrayDeque<Char>()
        row.forEach {
            when (it) {
                '(' -> chars += ')'
                '[' -> chars += ']'
                '{' -> chars += '}'
                '<' -> chars += '>'
                else -> {
                    val expected = chars.removeLastOrNull()
                    if (expected != null && expected != it) {
                        return@map when (it) {
                            ')' -> 3
                            ']' -> 57
                            '}' -> 1197
                            '>' -> 25137
                            else -> error("Unknown symbol: $it")
                        }
                    }
                }
            }
        }
        0
    }.sum()

    fun part2(input: List<String>): Long = input.mapNotNull { row ->
        val chars = ArrayDeque<Char>()
        row.forEach {
            when (it) {
                '(' -> chars += ')'
                '[' -> chars += ']'
                '{' -> chars += '}'
                '<' -> chars += '>'
                else -> {
                    val expected = chars.removeLastOrNull()
                    if (expected != null && expected != it) {
                        return@mapNotNull null
                    }
                }
            }
        }
        chars.reversed().fold(0L) { acc, char ->
            acc * 5 + when (char) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> error("Unexpected symbol: $char")
            }
        }
    }
        .sorted()
        .let { it[it.size / 2] }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
