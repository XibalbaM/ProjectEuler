package fr.xibalba.utils

import java.io.File
import java.util.function.Predicate

val abundantNumbers: MutableList<Long> by lazy {
    val file = File("src/main/resources/abundantNumbers.txt")
    if (file.exists()) file.readLines().map { it.toLong() }.toMutableList()
    else mutableListOf(12L)
}

private fun writeAbundantNumbersToFile() {
    val file = File("src/main/resources/abundantNumbers.txt")
    file.writeText(abundantNumbers.joinToString("\n"))
}

fun searchAbundantNumbersUntil(condition: Predicate<List<Long>>) {
    while (!condition.test(abundantNumbers))
        abundantNumbers.add(findNextAbundantNumber())
    writeAbundantNumbersToFile()
}

tailrec fun findNextAbundantNumber(number: Long = abundantNumbers.last()): Long =
     if (isAbundantNumber(number + 1)) {
        number + 1
    }
    else findNextAbundantNumber(number + 1)

fun isAbundantNumber(number: Long): Boolean = number.divisors().sum() > number