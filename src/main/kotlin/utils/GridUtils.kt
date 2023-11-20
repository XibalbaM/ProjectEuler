package fr.xibalba.utils

import kotlin.math.ceil

typealias Grid<T> = List<List<T>>

enum class GridDirection(val x: Int, val y: Int) {
    UP_LEFT(-1, -1),
    UP(0, -1),
    UP_RIGHT(1, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN_LEFT(-1, 1),
    DOWN(0, 1),
    DOWN_RIGHT(1, 1)
}

fun getNumbersInDirection(grid: Grid<Int>, x: Int, y: Int, direction: GridDirection, length: Int): List<Int> {
    if (x + direction.x * (length - 1) !in 0..<grid[y].size || y + direction.y * (length - 1) !in grid.indices) return emptyList()
    val numbers = mutableListOf<Int>()
    var xTemp = x
    var yTemp = y

    for (i in 0..<length) {
        numbers.add(grid[yTemp][xTemp])
        xTemp += direction.x
        yTemp += direction.y
    }

    return numbers
}

fun getAllPossibleSetsOfLengthInAllDirections(grid: Grid<Int>, length: Int): Grid<Int> {
    val sets = mutableListOf<List<Int>>()

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            for (direction in GridDirection.entries) {
                if (x + direction.x * (length - 1) in 0..<grid[y].size && y + direction.y * (length - 1) in grid.indices) {
                    sets.add(getNumbersInDirection(grid, x, y, direction, length))
                }
            }
        }
    }

    return sets.filter { it.size == length }
}
fun Grid<Any?>.layers(): Int = ceil(this.size/2.0).toInt()
fun Grid<Any?>.toCleanString(): String =
    this.joinToString("\n") {
        items -> items.joinToString(" ") { item ->
        item.toString().padStart(this.flatten().maxOfOrNull { it.toString().length } ?: 0, ' ')
        }
    }
fun Grid<Int>.addSpiralLayer(): Grid<Int> {
    val newGrid = mutableListOf<MutableList<Int>>()
    val size = this.size + 2
    val last = this.last().last()
    val numbers = ((last + 1)..size.pow(2).toInt()).toList()
    newGrid.add(MutableList(size) { -1 })
    newGrid.addAll(this.map { (listOf(-1) + it + -1).toMutableList() })
    newGrid.add(MutableList(size) { -1 })
    for (index in 0..<size) {
        when (index) {
            0 -> for (i in 0..<size) {
                newGrid[index][i] = numbers[2 * layers() + size - i - 2]
            }
            newGrid.lastIndex -> for (i in 0..<size) {
                newGrid[index][i] = numbers[4 * layers() + size + i - 2]
            }
            else -> {
                newGrid[index][newGrid[index].lastIndex] = numbers[(layers() * 2) - 1 - index]
                newGrid[index][0] = numbers[(layers() * 2) - 2 + size + index]
            }
        }
    }
    return newGrid
}
fun <T> Grid<T>.getDiagonals(): List<T> {
    val middle = this.size / 2
    val diagonals = mutableListOf(this[middle][middle])
    for (i in 1..middle) {
        diagonals += this[middle - i][middle - i]
        diagonals += this[middle + i][middle - i]
        diagonals += this[middle - i][middle + i]
        diagonals += this[middle + i][middle + i]
    }
    return diagonals
}