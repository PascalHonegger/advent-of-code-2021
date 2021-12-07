fun main() {
    fun simulateLanternFish(input: List<Int>, cycles: Int): Long {
        val lanternFish = LongArray(9)
        input.forEach { lanternFish[it]++ }
        repeat(cycles) {
            val newGeneration = lanternFish[0]
            for (i in 0 until 8) {
                lanternFish[i] = lanternFish[i + 1]
            }
            lanternFish[6] += newGeneration
            lanternFish[8] = newGeneration
        }
        return lanternFish.sum()
    }

    val testInput = readWholeInputAsInts("Day06_test", ',')
    check(simulateLanternFish(testInput, 80) == 5934L)
    check(simulateLanternFish(testInput, 256) == 26984457539L)

    val input = readWholeInputAsInts("Day06", ',')
    println(simulateLanternFish(input, 80))
    println(simulateLanternFish(input, 256))
}
