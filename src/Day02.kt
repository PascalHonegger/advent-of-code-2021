private sealed interface Direction

private data class Forward(val amount: Int) : Direction
private data class Down(val amount: Int) : Direction
private data class Up(val amount: Int) : Direction

private data class Steering(
    val depth: Int = 0,
    val horizontal: Int = 0,
    val aim: Int = 0
) {
    val total get() = depth * horizontal
}

fun main() {
    fun createDirection(type: String, amount: Int) = when (type) {
        "forward" -> Forward(amount)
        "down" -> Down(amount)
        "up" -> Up(amount)
        else -> error("Unknown type $type")
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(' ') }
            .map { (type, amount) -> createDirection(type, amount.toInt()) }
            .fold(Steering()) { current, step ->
                when (step) {
                    is Down -> current.copy(depth = current.depth + step.amount)
                    is Up -> current.copy(depth = current.depth - step.amount)
                    is Forward -> current.copy(horizontal = current.horizontal + step.amount)
                }
            }.total
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.split(' ') }
            .map { (type, amount) -> createDirection(type, amount.toInt()) }
            .fold(Steering()) { current, step ->
                when (step) {
                    is Down -> current.copy(aim = current.aim + step.amount)
                    is Up -> current.copy(aim = current.aim - step.amount)
                    is Forward -> current.copy(
                        horizontal = current.horizontal + step.amount,
                        depth = current.depth + current.aim * step.amount
                    )
                }
            }.total
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
