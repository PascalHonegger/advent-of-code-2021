fun main() {
    data class Coordinate(val row: Int, val col: Int)

    operator fun Array<IntArray>.get(it: Coordinate): Int = get(it.row)[it.col]
    operator fun Array<IntArray>.set(it: Coordinate, value: Int) {
        get(it.row)[it.col] = value
    }

    operator fun List<String>.get(it: Coordinate): Int =
        get(it.row)[it.col].digitToInt()

    operator fun Array<IntArray>.contains(it: Coordinate): Boolean =
        getOrNull(it.row)?.getOrNull(it.col) != null

    operator fun List<String>.contains(it: Coordinate): Boolean =
        getOrNull(it.row)?.getOrNull(it.col) != null

    fun Char.increaseDigit(amount: Int): Char =
        (digitToInt() + amount).let { if (it > 9) it % 9 else it }.digitToChar()

    fun dijkstra(costs: List<String>): Int {
        val width = costs.first().length
        val height = costs.size

        val distances = Array(height) { IntArray(width) { Int.MAX_VALUE } }
        distances[0][0] = 0
        distances[1][0] = costs[1][0].digitToInt()
        distances[0][1] = costs[0][1].digitToInt()
        val nodesToCheck = mutableSetOf(Coordinate(0, 1), Coordinate(1, 0))
        while (nodesToCheck.isNotEmpty()) {
            val current = nodesToCheck.minByOrNull { distances[it] }!!
            nodesToCheck.remove(current)
            sequenceOf(
                current.copy(row = current.row - 1),
                current.copy(row = current.row + 1),
                current.copy(col = current.col - 1),
                current.copy(col = current.col + 1),
            )
                .filter { it in distances }
                .forEach {
                    val newCost = distances[current] + costs[it]
                    if (newCost < distances[it]) {
                        distances[it] = newCost
                        nodesToCheck += it
                    }
                }

            if (distances.last().last() != Int.MAX_VALUE) {
                return distances.last().last()
            }
        }
        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int = dijkstra(input)

    fun part2(input: List<String>): Int {
        val scale = 5
        val height = input.size

        val newInput = buildList {
            repeat(height * scale) { row ->
                val rowOffset = row / height
                add(buildString {
                    val origRow = input[row % height]
                    repeat(scale) { col ->
                        origRow.forEach { append(it.increaseDigit(rowOffset + col)) }
                    }
                })
            }
        }

        return dijkstra(newInput)
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
