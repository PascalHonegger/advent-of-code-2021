fun main() {

    fun Iterable<String>.partitionBits(index: Int) =
        this.partition { it[index] == '1' }

    fun takeLarger(first: List<String>, second: List<String>) =
        if (first.size >= second.size) first else second

    fun takeSmaller(first: List<String>, second: List<String>) =
        if (first.size <= second.size) first else second

    fun part1(input: List<String>): Int {
        val nbits = input.first().length

        val gammaRate = (0 until nbits)
            .map { input.partitionBits(it) }
            .map { (ones, zeroes) -> ones.size > zeroes.size }
            .joinToString(separator = "") { if (it) "1" else "0" }
            .toInt(radix = 2)

        val invertLowerBitMask = 0.inv() ushr (Int.SIZE_BITS - nbits)
        val epsilonRate = gammaRate xor invertLowerBitMask
        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val nbits = input.first().length

        fun getReadings(groupSelector: (Pair<List<String>, List<String>>) -> List<String>): Int {
            var workingSet = input
            for (index in 0 until nbits) {
                workingSet = groupSelector(workingSet.partitionBits(index))

                if (workingSet.size == 1) {
                    return workingSet[0].toInt(radix = 2)
                }
            }
            error("Empty input is not supported")
        }

        val oxygen = getReadings { (ones, zeroes) -> takeLarger(ones, zeroes) }
        val co2 = getReadings { (ones, zeroes) -> takeSmaller(zeroes, ones) }
        return oxygen * co2
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
