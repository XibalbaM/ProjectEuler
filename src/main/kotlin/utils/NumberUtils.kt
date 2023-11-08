package fr.xibalba.utils

import java.math.BigInteger

tailrec fun palindromeProducts(
    maxDigitsOfFactors: Int,
    firstFactor: Int = "9".repeat(maxDigitsOfFactors).toInt(),
    secondFactor: Int = firstFactor,
    list: List<Int> = emptyList()
): List<Int> =
    if (firstFactor > 0) {
        if (secondFactor > 0) {
            if (isPalindrome(firstFactor * secondFactor)) palindromeProducts(
                maxDigitsOfFactors,
                firstFactor,
                secondFactor - 1,
                list.plus(firstFactor * secondFactor)
            )
            else palindromeProducts(maxDigitsOfFactors, firstFactor, secondFactor - 1, list)
        } else palindromeProducts(maxDigitsOfFactors, firstFactor - 1, "9".repeat(maxDigitsOfFactors).toInt(), list)
    } else list

fun isPalindrome(number: Int): Boolean {
    val numberString = number.toString()
    val numberStringReversed = numberString.reversed()
    return numberString == numberStringReversed
}

tailrec fun smallestNumberDivisibleByAllNumbersInRange(range: IntRange, number: Int = 1): Int =
    if (range.all { number % it == 0 }) number
    else smallestNumberDivisibleByAllNumbersInRange(range, number + 1)

fun isPythagoreanTriplet(triplet: Triple<Int, Int, Int>): Boolean =
    triplet.first * triplet.first + triplet.second * triplet.second == triplet.third * triplet.third

fun findPythagoreanTripletFromLastElement(element: Int): List<Triple<Int, Int, Int>> =
    (1..element)
        .map { Triple(sqrtOfAfterPuttingToSquare(element, it) { first, second -> first - second }, it, element) }
        .filter { isPythagoreanTriplet(Triple(it.first, it.second, element)) }

fun Int.toWords(): String {
    var number = this.toString()
    val thousands = Regex("(\\d{1,3})\\d{3}").find(number)?.groupValues?.get(1)?.toInt()?.toWords()?.plus(" thousand") ?: ""
    if (thousands.isNotBlank()) number = number.substring(number.length - 3)
    val hundreds = Regex("(\\d{1,3})\\d{2}").find(number)?.groupValues?.get(1)?.toInt()?.toWords()?.ifNotBlank { plus(" hundred") } ?: ""
    val tens = number.takeLast(2).toInt()
    if (tens >= 20) {
        val tensString = when (tens / 10) {
            2 -> "twenty"
            3 -> "thirty"
            4 -> "forty"
            5 -> "fifty"
            6 -> "sixty"
            7 -> "seventy"
            8 -> "eighty"
            9 -> "ninety"
            else -> ""
        }
        val unitsString = when (tens % 10) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            4 -> "four"
            5 -> "five"
            6 -> "six"
            7 -> "seven"
            8 -> "eight"
            9 -> "nine"
            else -> ""
        }
        return "$thousands $hundreds ${if (hundreds.isNotBlank()) "and " else ""}$tensString $unitsString".trim()
    } else {
        val unitsString = when (tens) {
            1 -> "one"
            2 -> "two"
            3 -> "three"
            4 -> "four"
            5 -> "five"
            6 -> "six"
            7 -> "seven"
            8 -> "eight"
            9 -> "nine"
            10 -> "ten"
            11 -> "eleven"
            12 -> "twelve"
            13 -> "thirteen"
            14 -> "fourteen"
            15 -> "fifteen"
            16 -> "sixteen"
            17 -> "seventeen"
            18 -> "eighteen"
            19 -> "nineteen"
            else -> ""
        }
        return "$thousands $hundreds ${if (hundreds.isNotBlank()) "and " else ""}$unitsString".trim()
    }
}

fun Number.factorial(): BigInteger = (1..this.toLong()).map { BigInteger.valueOf(it) }.reduce { acc, i -> acc * i }

fun Number.digits(): List<Byte> = this.toString().map { it.toString().toByte() }

fun findAmicableNumbersUnder(number: Int): List<Long> {
    val amicableNumbers = mutableListOf<Long>()
    for (i in 1L..<number) {
        val sumOfDivisors = i.divisors().sum()
        if (sumOfDivisors != i && sumOfDivisors.divisors().sum() == i) {
            amicableNumbers.add(i)
        }
    }
    return amicableNumbers
}