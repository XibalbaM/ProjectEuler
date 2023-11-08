package fr.xibalba.utils

import kotlin.math.pow
import kotlin.math.sqrt

tailrec fun multiplesOfUntil(number: Int, until: Int, multiplier: Int = 0, list: List<Int> = emptyList()): List<Int> =
    if (number * multiplier < until) multiplesOfUntil(number, until, multiplier + 1, list.plus(number * multiplier))
    else list

fun sqrtOfAfterPuttingToSquare(first: Int, second: Int, operation: (first: Int, second: Int) -> Int): Int
    = sqrt(operation(first * first, second * second).toDouble()).toInt()

fun getDivisorsCount(number: Long): Int {
    var count = 0
    val sqrt = sqrt(number.toDouble()).toLong()

    for (i in 1..sqrt) {
        if (number % i == 0L) {
            count += 2
        }
    }

    return count
}

fun Number.divisors(): List<Long> {
    val divisors = mutableListOf<Long>()
    val asLong = this.toLong()
    for (i in 1L..<asLong) {
        if (asLong % i == 0L) {
            divisors.add(i)
        }
    }
    return divisors
}

infix fun Number.pow(exponent: Number): Double = this.toDouble().pow(exponent.toDouble())

fun findAllDecompositionsInTwoMultiples(number: Int): List<Pair<Int, Int>> {
    val decompositions = mutableListOf<Pair<Int, Int>>()
    val divisors = number.divisors().toMutableList()
    for (i in 0..divisors.size) {
        if (i >= divisors.size) {
            break
        }
        val divisor = divisors[i]
        val quotient = number / divisor
        if (quotient in divisors) {
            decompositions.add(Pair(divisor.toInt(), quotient.toInt()))
            divisors.remove(quotient)
        }
    }
    return decompositions
}