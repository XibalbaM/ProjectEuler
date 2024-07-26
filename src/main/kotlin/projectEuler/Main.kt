package fr.xibalba.projectEuler

import kotlinx.coroutines.runBlocking
import java.io.File

fun main() = runBlocking {
    val data = File("src/main/resources/poker_hands.txt").readLines()
    val duels = data.map { Duel(it) }
    duels.forEach {
        println(it.getWinner())
    }
    println(duels.count { it.getWinner() > 0 })
}

data class Card(val value: CardValue, val color: CardColor) {

    constructor(card: String) : this(
        CardValue.fromChar(card.first()),
        CardColor.fromChar(card.last())
    )
}

enum class CardValue(val value: Int) {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    companion object {
        fun fromChar(char: Char) = when (char) {
            '2' -> TWO
            '3' -> THREE
            '4' -> FOUR
            '5' -> FIVE
            '6' -> SIX
            '7' -> SEVEN
            '8' -> EIGHT
            '9' -> NINE
            'T' -> TEN
            'J' -> JACK
            'Q' -> QUEEN
            'K' -> KING
            'A' -> ACE
            else -> throw IllegalArgumentException("Invalid card value")
        }
    }
}

enum class CardColor {
    HEARTS,
    DIAMONDS,
    CLUBS,
    SPADES;

    companion object {
        fun fromChar(char: Char) = when (char) {
            'H' -> HEARTS
            'D' -> DIAMONDS
            'C' -> CLUBS
            'S' -> SPADES
            else -> throw IllegalArgumentException("Invalid card color")
        }
    }
}

data class Hand(val cards: List<Card>) {
    init {
        require(cards.size == 5) { "A hand must have 5 cards" }
    }

    constructor(hand: String) : this(hand.split(" ").map { Card(it) })

    fun getValue(): HandValue {
        return when {
            isRoyalFlush() -> HandValue.RoyalFlush(this)
            isStraightFlush() -> HandValue.StraightFlush(this)
            isFourOfAKind() -> HandValue.FourOfAKind(this)
            isFullHouse() -> HandValue.FullHouse(this)
            isFlush() -> HandValue.Flush(this)
            isStraight() -> HandValue.Straight(this)
            isThreeOfAKind() -> HandValue.ThreeOfAKind(this)
            isTwoPairs() -> HandValue.TwoPairs(this)
            isPair() -> HandValue.Pair(this)
            else -> HandValue.HighCard(this)
        }
    }
}

data class Duel(val hands: Pair<Hand, Hand>) {

    constructor(duel: String) : this(
        Pair(
            Hand(duel.substring(0, 14)),
            Hand(duel.substring(15))
        )
    )

    fun getWinner(): Int {
        return hands.first.getValue().compareTo(hands.second.getValue())
    }
}

sealed class HandValue(val hand: Hand) {
    abstract val value: Int

    class HighCard(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.maxOf { it.value.value }
    }

    class Pair(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.groupBy { it.value }.values.first { it.size == 2}.first().value.value
    }

    class TwoPairs(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.groupBy { it.value }.values.filter { it.size == 2}.maxOf { it.first().value.value }
    }

    class ThreeOfAKind(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.groupBy { it.value }.values.first { it.size == 3}.first().value.value
    }

    class Straight(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.minOf { it.value.value }
    }

    class Flush(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.maxOf { it.value.value }
    }

    class FullHouse(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.groupBy { it.value }.values.first { it.size == 3}.first().value.value
    }

    class FourOfAKind(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.groupBy { it.value }.values.first { it.size == 4}.first().value.value
    }

    class StraightFlush(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.minOf { it.value.value }
    }

    class RoyalFlush(hand: Hand) : HandValue(hand) {
        override val value = hand.cards.minOf { it.value.value }
    }
}

val handKinds = listOf(
    HandValue.HighCard::class,
    HandValue.Pair::class,
    HandValue.TwoPairs::class,
    HandValue.ThreeOfAKind::class,
    HandValue.Straight::class,
    HandValue.Flush::class,
    HandValue.FullHouse::class,
    HandValue.FourOfAKind::class,
    HandValue.StraightFlush::class,
    HandValue.RoyalFlush::class
)

operator fun HandValue.compareTo(other: HandValue): Int {
    val thisIndex = handKinds.indexOf(this::class)
    val otherIndex = handKinds.indexOf(other::class)
    return thisIndex.compareTo(otherIndex).takeIf { it != 0 } ?: value.compareTo(other.value).takeIf { it != 0 } ?: hand.cards.maxOf { it.value.value }.compareTo(other.hand.cards.maxOf { it.value.value })
}

fun Hand.isRoyalFlush() = isStraightFlush() && cards.minOf { it.value } == CardValue.TEN

fun Hand.isStraightFlush() = isFlush() && isStraight()

fun Hand.isFourOfAKind() = cards.groupBy { it.value }.values.any { it.size == 4 }

fun Hand.isFullHouse() = cards.groupBy { it.value }.values.any { it.size == 3 } && cards.groupBy { it.value }.values.any { it.size == 2 }

fun Hand.isFlush() = cards.groupBy { it.color }.size == 1

fun Hand.isStraight() = cards.map { it.value.value }.sorted().let { it.last() - it.first() == 4 && it.toSet().size == 5 }

fun Hand.isThreeOfAKind() = cards.groupBy { it.value }.values.any { it.size == 3 }

fun Hand.isTwoPairs() = cards.groupBy { it.value }.values.count { it.size == 2 } == 2

fun Hand.isPair() = cards.groupBy { it.value }.values.any { it.size == 2 }