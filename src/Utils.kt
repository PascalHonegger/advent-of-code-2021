import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads one int per line from the given input txt file.
 */
fun readInputAsInts(name: String) = readInput(name).map { it.toInt() }

/**
 * Split a string into ints.
 */
fun String.splitAsInts(delimiter: Char): List<Int> =
    split(delimiter).filter { it.isNotBlank() }.map { it.trim().toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(
    1,
    MessageDigest.getInstance("MD5").digest(toByteArray())
).toString(16)
