fun main() {
    fun String.normalized() = String(toCharArray().sortedArray())

    fun part1(input: List<String>): Int = input
        .flatMap { it.split('|').last().split(' ') }
        .count { it.length in listOf(2, 3, 4, 7) }

    fun part2(input: List<String>): Int = input.sumOf { row ->
        val (training, real) = row.split("| ")
        val digits = training.split(' ')
        val two = digits.single { it.length == 2 }
        val four = digits.single { it.length == 4 }
        val seven = digits.single { it.length == 3 }
        val eight = digits.single { it.length == 7 }

        fun Char.totalOccurrences() = training.count { it == this }

        val a = seven.single { it !in two }
        val b = four.single { it.totalOccurrences() == 6 }
        val c = two.single { it.totalOccurrences() == 8 }
        val d = four.single { it.totalOccurrences() == 7 }
        val e = eight.single { it.totalOccurrences() == 4 }
        val f = two.single { it.totalOccurrences() == 9 }
        val g = eight.single { it !in listOf(a, b, c, d, e, f) }

        val lookup = mapOf(
            "$a$b$c$e$f$g".normalized() to "0",
            "$c$f".normalized() to "1",
            "$a$c$d$e$g".normalized() to "2",
            "$a$c$d$f$g".normalized() to "3",
            "$b$c$d$f".normalized() to "4",
            "$a$b$d$f$g".normalized() to "5",
            "$a$b$d$e$f$g".normalized() to "6",
            "$a$c$f".normalized() to "7",
            "$a$b$c$d$e$f$g".normalized() to "8",
            "$a$b$c$d$f$g".normalized() to "9",
        )

        real.split(' ')
            .joinToString("") {
                lookup[it.normalized()]
                    ?: error("Couldn't map $it in ${lookup.keys}")
            }
            .toInt()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
