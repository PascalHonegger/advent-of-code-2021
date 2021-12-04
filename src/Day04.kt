private typealias Bingo = List<IntArray>

private fun List<String>.toBingos(): List<Bingo> {
    return filter { it.isNotEmpty() }
        .chunked(5) { rows ->
            rows.map { it.splitAsInts(' ').toIntArray() }
        }
}

private fun Bingo.mark(number: Int) {
    for (row in this) {
        val item = row.indexOf(number)
        if (item != -1) {
            row[item] = -1
            break
        }
    }
}

private val Bingo.sumUnmarkedNumbers
    get() = sumOf { row ->
        row.filter { it != -1 }.sum()
    }

private val Bingo.isBingo get() = isRowBingo || isColBingo

private val Bingo.isRowBingo
    get() = any { row ->
        row.all { it == -1 }
    }

private val Bingo.isColBingo
    get() = (0..4).any { col ->
        all { it[col] == -1 }
    }

fun main() {
    fun part1(input: List<String>): Int {
        val calledNumbers = input.first().splitAsInts(',')
        val bingos = input.drop(1).toBingos()

        for (number in calledNumbers) {
            for (bingo in bingos) {
                bingo.mark(number)

                if (bingo.isBingo) {
                    return number * bingo.sumUnmarkedNumbers
                }
            }
        }

        error("No bingo found")
    }

    fun part2(input: List<String>): Int {
        val calledNumbers = input.first().splitAsInts(',')
        val bingos = input.drop(1).toBingos().toMutableList()

        for (number in calledNumbers) {
            for (bingo in bingos.toList()) {
                bingo.mark(number)

                if (bingo.isBingo) {
                    bingos -= bingo
                    if (bingos.isEmpty()) {
                        return number * bingo.sumUnmarkedNumbers
                    }
                }
            }
        }

        error("No bingo found")
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
