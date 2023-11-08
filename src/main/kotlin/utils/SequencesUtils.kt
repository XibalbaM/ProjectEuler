package fr.xibalba.utils

import java.math.BigInteger
import java.util.function.Predicate

tailrec fun fibonacciUntil(list: List<BigInteger> = listOf(BigInteger.ONE, BigInteger.TWO), until: Predicate<List<BigInteger>>): List<BigInteger> =
    if (!until.test(list)) fibonacciUntil(list.plus(list.last() + list.dropLast(1).last()), until)
    else list