fun main() {
    data class Point(val x: Int, val y: Int)
    data class Fold(val axis: Char, val position: Int)

    fun String.toPoint() =
        split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }

    fun String.toFold() = """fold along ([xy])=(\d+)""".toRegex()
        .matchEntire(this)?.destructured?.let { (axis, position) ->
            Fold(axis.single(), position.toInt())
        } ?: error("Unable to parse fold: $this")

    fun part1(input: List<String>): Int {
        val points = input.takeWhile { it.isNotEmpty() }.map { it.toPoint() }
        val folds = input.takeLastWhile { it.isNotEmpty() }.map { it.toFold() }

        val fold = folds.first()
        return when (fold.axis) {
            'x' -> points.map { if (it.x < fold.position) it else it.copy(x = fold.position * 2 - it.x) }
            'y' -> points.map { if (it.y < fold.position) it else it.copy(y = fold.position * 2 - it.y) }
            else -> error("Unexpected axis: ${fold.axis}")
        }.distinct().size
    }

    fun part2(input: List<String>): String {
        var points = input.takeWhile { it.isNotEmpty() }.map { it.toPoint() }
        val folds = input.takeLastWhile { it.isNotEmpty() }.map { it.toFold() }

        folds.forEach { fold ->
            points = when (fold.axis) {
                'x' -> points.map { if (it.x < fold.position) it else it.copy(x = fold.position * 2 - it.x) }
                'y' -> points.map { if (it.y < fold.position) it else it.copy(y = fold.position * 2 - it.y) }
                else -> error("Unexpected axis: ${fold.axis}")
            }.distinct()
        }
        return buildString {
            for (y in 0..points.maxOf { it.y }) {
                for (x in 0..points.maxOf { it.x }) {
                    append(if (points.any { it.x == x && it.y == y }) '#' else ' ')
                }
                appendLine()
            }
        }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput).trimIndent() == """
        #####
        #   #
        #   #
        #   #
        #####
""".trimIndent())

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
