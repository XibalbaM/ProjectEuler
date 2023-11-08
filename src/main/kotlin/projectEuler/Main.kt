package fr.xibalba.projectEuler

import fr.xibalba.utils.primeNumbers
import kotlinx.coroutines.*


fun main() {
    val primeNumbersAsStrings = primeNumbers.map { it.toString() }
    val jobs: MutableList<Job> = mutableListOf()

    (1..8).forEach {id ->
        CalculatorThread(id, primeNumbersAsStrings, threads) { println("Thread #$id found :\n$it") }.apply { threads.add(this); this.start() }
    }

}

fun calculate(i: Int, primeAsStrings: List<String>) {
        val usedPrimes = primeAsStrings.filter { it.length > i }
        usedPrimes
            .asSequence()
            .flatMap { string -> string.toCharArray().groupBy { it }.filterValues {  it.size >= i }.keys.map { string to it } }
            .filter { (string, doubleDigit) -> usedPrimes.count { string.replaceFirst(doubleDigit.toString(), "(\\d)").replace(doubleDigit.toString(), "(?:\\1)").toRegex().matches(it) } >= 8 }
            .map { it.first }
            .map { it.toInt() }
            .sorted()
            .forEach { println(it) }
}

fun findPrimesWithConstantSpacing() {

}