private class DumboOctopus(
    var energyLevel: Int,
    var neighbours: List<DumboOctopus> = mutableListOf(),
) {
    fun resetFlash(): Boolean {
        return if (energyLevel > 9) {
            energyLevel = 0
            true
        } else {
            false
        }
    }

    fun increase() {
        energyLevel++
        if (energyLevel == 10) {
            neighbours.forEach { it.increase() }
        }
    }

    override fun toString() = energyLevel.toString()
}

fun main() {
    fun List<String>.toOctopuses(): List<DumboOctopus> {
        val dumboOctopuses = flatMap { row -> row.map { DumboOctopus(it.digitToInt()) } }
        dumboOctopuses.forEachIndexed { index, dumboOctopus ->
            val isFirstColumn = index % 10 == 0
            val isLastColumn = (index + 1) % 10 == 0
            dumboOctopus.neighbours = listOfNotNull(
                dumboOctopuses.getOrNull(index - 11)?.takeIf { !isFirstColumn},
                dumboOctopuses.getOrNull(index - 1)?.takeIf { !isFirstColumn},
                dumboOctopuses.getOrNull(index + 9)?.takeIf { !isFirstColumn},
                dumboOctopuses.getOrNull(index - 10),
                dumboOctopuses.getOrNull(index + 10),
                dumboOctopuses.getOrNull(index - 9)?.takeIf { !isLastColumn },
                dumboOctopuses.getOrNull(index + 1)?.takeIf { !isLastColumn },
                dumboOctopuses.getOrNull(index + 11)?.takeIf { !isLastColumn },
            )
        }
        return dumboOctopuses
    }

    fun part1(input: List<String>): Int {
        val dumboOctopuses = input.toOctopuses()

        var flashCount = 0
        repeat(100) {
            dumboOctopuses.forEach { it.increase() }
            flashCount += dumboOctopuses.count { it.resetFlash() }
        }
        return flashCount
    }

    fun part2(input: List<String>): Int {
        val dumboOctopuses = input.toOctopuses()

        for(cycle in 1..999) {
            dumboOctopuses.forEach { it.increase() }
            if (dumboOctopuses.count { it.resetFlash() } == dumboOctopuses.size) {
                return cycle
            }
        }
        error("No flash sync found in reasonable time")
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
