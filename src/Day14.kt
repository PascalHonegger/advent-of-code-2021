fun main() {
    fun <T> MutableMap<T, Long>.incrementCount(key: T, amount: Long = 1) {
        compute(key) { _, count -> (count ?: 0) + amount }
    }

    fun <T> MutableMap<T, Long>.decrementCount(key: T, amount: Long = 1) {
        val newValue = compute(key) { _, count -> (count ?: 0) - amount }
        checkNotNull(newValue)
        check(newValue >= 0) { "Cannot have negative amount" }
        if (newValue == 0L) {
            remove(key)
        }
    }

    fun part1(input: List<String>): Int {
        val startingPattern = input.first()
        val patterns = input.drop(2).map { it.split(" -> ") }
            .associate { it.first() to it.last() }

        var pattern = startingPattern
        repeat(10) {
            pattern = buildString {
                pattern.windowed(size = 2, partialWindows = true) {
                    append(it.first())
                    if (it.length == 2) {
                        patterns[it]?.let { interop -> append(interop) }
                    }
                }
            }
        }
        val occurs = pattern.groupingBy { it }.eachCount().entries
        return occurs.maxOf { it.value } - occurs.minOf { it.value }
    }

    fun part2(input: List<String>): Long {
        val startingPattern = input.first()
        val patterns = input.drop(2).map { it.split(" -> ") }
            .associate { it.first() to it.last().single() }

        val letterOccurs = mutableMapOf<Char, Long>()
        val patternOccurs = mutableMapOf<String, Long>()

        startingPattern.forEach {
            letterOccurs.incrementCount(it)
        }
        startingPattern.windowed(2) {
            patternOccurs.incrementCount(it.toString())
        }

        repeat(40) {
            patternOccurs.toMap().entries.forEach { (pattern, count) ->
                patterns[pattern]?.let {
                    letterOccurs.incrementCount(it, count)
                    patternOccurs.decrementCount(pattern, count)
                    patternOccurs.incrementCount("${pattern.first()}$it", count)
                    patternOccurs.incrementCount("$it${pattern.last()}", count)
                }
            }
        }
        return letterOccurs.maxOf { it.value } - letterOccurs.minOf { it.value }
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
