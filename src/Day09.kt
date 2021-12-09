fun main() {
    fun part1(input: List<String>): Int {
        var riskLevel = 0

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, height ->
                val smallerThanTop =
                    input.getOrNull(rowIndex - 1)?.let { height < it[colIndex] }
                        ?: true
                val smallerThanBottom =
                    input.getOrNull(rowIndex + 1)?.let { height < it[colIndex] }
                        ?: true
                val smallerThanLeft =
                    row.getOrNull(colIndex - 1)?.let { height < it } ?: true
                val smallerThanRight =
                    row.getOrNull(colIndex + 1)?.let { height < it } ?: true
                if (smallerThanTop && smallerThanBottom && smallerThanLeft && smallerThanRight) {
                    riskLevel += height.digitToInt() + 1
                }
            }
        }

        return riskLevel
    }

    fun part2(input: List<String>): Int {
        // Map index to basin number
        val basins = Array(input.size) { IntArray(input.first().length) }
        var currentBasin = 1

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, height ->
                val top = basins.getOrNull(rowIndex - 1)?.get(colIndex) ?: 0
                val bot = basins.getOrNull(rowIndex + 1)?.get(colIndex) ?: 0
                val left = basins[rowIndex].getOrNull(colIndex - 1) ?: 0
                val right = basins[rowIndex].getOrNull(colIndex + 1) ?: 0
                basins[rowIndex][colIndex] = when {
                    height == '9' -> 0
                    top != 0 -> top
                    bot != 0 -> bot
                    left != 0 -> left
                    right != 0 -> right
                    else -> currentBasin++
                }

                // Replace top with left for consistency
                if (height != '9' && top != 0 && left != 0 && top != left) {
                    basins.forEach { row ->
                        row.toList().forEachIndexed { index, basin ->
                            if (basin == top) {
                                row[index] = left;
                            }
                        }
                    }
                }
            }
        }

        return basins
            .asSequence()
            .flatMap { it.asSequence() }
            .filter { it != 0 }
            .groupingBy { it }
            .eachCount()
            .values
            .sortedDescending()
            .take(3)
            .reduce { a, b -> a * b }
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
