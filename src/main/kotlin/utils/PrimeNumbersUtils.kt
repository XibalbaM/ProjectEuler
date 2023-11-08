package fr.xibalba.utils

import java.io.File
import java.util.function.Predicate

val primeNumbers: MutableList<Long> by lazy {
    val file = File("src/main/resources/primeNumbers.txt")
    if (file.exists()) file.readLines().map { it.toLong() }.toMutableList()
    else mutableListOf(2L, 3L)
}

private fun writePrimeNumbersToFile() {
    val file = File("src/main/resources/primeNumbers.txt")
    file.writeText(primeNumbers.joinToString("\n"))
}

fun searchPrimeNumbersUntil(condition: Predicate<List<Long>>) {
    while (!condition.test(primeNumbers))
        primeNumbers.add(findNextPrimeNumber())
    writePrimeNumbersToFile()
}

tailrec fun primeFactors(number: Long, factors: List<Long> = emptyList()): List<Long> {
    searchPrimeNumbersUntil { it.last() > number }
    return if (number == 1L || number == 0L) factors else {
        val primeFactor = primeNumbers.first { number % it == 0L }
        primeFactors(number / primeFactor, factors.plus(primeFactor))
    }
}

tailrec fun findNextPrimeNumber(number: Long = primeNumbers.last()): Long =
    if (isNewPrime(number + 2)) {
        number + 2
    }
    else findNextPrimeNumber(number + 2)

fun isNewPrime(value: Long): Boolean = primeNumbers.none { value % it == 0L }

fun isPrime(value: Long): Boolean = primeNumbers.contains(value)