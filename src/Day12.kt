fun main() {
    class Cave(
        val name: String,
        val connections: MutableList<Cave> = mutableListOf(),
    ) {
        val isStart = name == "start"
        val isEnd = name == "end"
        val isSmall = name.all { it.isLowerCase() }
        val isBig = name.all { it.isUpperCase() }
        override fun toString() = name
    }

    fun List<String>.parseCaves(): List<Cave> {
        val caveNames = groupBy(
            keySelector = { row -> row.takeWhile { it != '-' } },
            valueTransform = { row -> row.takeLastWhile { it != '-' } }
        )
        val caves = mutableMapOf<String, Cave>()
        caveNames.forEach { (sourceName, destName) ->
            val sourceCave = caves.getOrPut(sourceName) { Cave(sourceName) }
            val destCaves = destName.map { caves.getOrPut(it) { Cave(it) } }
            sourceCave.connections += destCaves
            destCaves.forEach { it.connections += sourceCave }
        }
        return caves.values.toList()
    }

    fun countPathsPart1(cave: Cave, visited: Set<Cave>): Int =
        cave.connections.map {
            when {
                it.isEnd -> 1
                it.isSmall && it in visited -> 0
                else -> countPathsPart1(it, visited + cave)
            }
        }.sum()

    fun countPathsPart2(cave: Cave, visited: Set<Cave>, visitedTwice: Cave?): Int =
        cave.connections.map {
            when {
                it.isEnd -> 1
                it.isStart -> 0
                it.isBig -> countPathsPart2(it, visited, visitedTwice)
                it in visited -> when(visitedTwice) {
                    null -> countPathsPart2(it, visited, it)
                    else -> 0
                }
                else -> countPathsPart2(it, visited + it, visitedTwice)
            }
        }.sum()

    fun part1(input: List<String>): Int {
        val caves = input.parseCaves()
        return countPathsPart1(caves.single { it.isStart }, emptySet())
    }

    fun part2(input: List<String>): Int {
        val caves = input.parseCaves()
        return countPathsPart2(caves.single { it.isStart }, emptySet(), null)
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
