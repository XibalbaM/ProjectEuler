package fr.xibalba.utils

import java.time.LocalDate
import java.util.*

operator fun LocalDate.rangeTo(other: LocalDate) = LocalDateRange(this, other)
operator fun LocalDate.rangeUntil(other: LocalDate) = LocalDateRange(this, other, true)
infix fun LocalDate.downTo(other: LocalDate) = LocalDateRange(other, this).reversed()

class LocalDateRange(localDate: LocalDate, other: LocalDate, until: Boolean = false) : ClosedRange<LocalDate>, OpenEndRange<LocalDate>, List<LocalDate> {
    private val list = buildList {
        var date = localDate
        while (if (!until) date <= other else date < other) {
            add(date)
            date = date.plusDays(1)
        }
    }
    override fun iterator(): Iterator<LocalDate> {
        return list.iterator()
    }

    override fun listIterator(): ListIterator<LocalDate> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): ListIterator<LocalDate> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<LocalDate> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: LocalDate): Int {
        TODO("Not yet implemented")
    }

    override val endInclusive: LocalDate
        get() = list.last()
    override val endExclusive: LocalDate
        get() = list.last()
    override val start: LocalDate
        get() = list.first()

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun contains(element: LocalDate): Boolean {
        return element in list
    }

    override fun toString(): String {
        return "LocalDateRange($list)"
    }

    override val size: Int
        get() = list.size

    override fun containsAll(elements: Collection<LocalDate>): Boolean {
        return list.containsAll(elements)
    }

    override fun equals(other: Any?): Boolean {
        return other is LocalDateRange && list == other.list
    }

    override fun get(index: Int): LocalDate {
        return list[index]
    }

    override fun hashCode(): Int {
        return list.hashCode()
    }

    override fun indexOf(element: LocalDate): Int {
        return list.indexOf(element)
    }

    fun toList(): List<LocalDate> {
        return list
    }

    fun toMutableList(): MutableList<LocalDate> {
        return list.toMutableList()
    }

    fun toSet(): Set<LocalDate> {
        return list.toSet()
    }

    fun toMutableSet(): MutableSet<LocalDate> {
        return list.toMutableSet()
    }

    fun toSortedSet(): SortedSet<LocalDate> {
        return list.toSortedSet()
    }
}