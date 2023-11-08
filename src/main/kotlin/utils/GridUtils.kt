package fr.xibalba.utils

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

fun getNumbersInDirection(grid: List<List<Int>>, x: Int, y: Int, direction: GridDirection, length: Int): List<Int> {
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

fun getAllPossibleSetsOfLengthInAllDirections(grid: List<List<Int>>, length: Int): List<List<Int>> {
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