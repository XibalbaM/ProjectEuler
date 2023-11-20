package fr.xibalba.utils

fun Char.xor(other: Char): Char = (this.code xor other.code).toChar()
infix fun String.xor(other: String) =
    other.repeatUntilSize(this.length).let { string -> this.mapIndexed { index, c -> c.xor(string[index]) }.joinToString("") }