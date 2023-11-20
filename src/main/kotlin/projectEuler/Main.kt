package fr.xibalba.projectEuler

import fr.xibalba.utils.xor
import kotlinx.coroutines.runBlocking
import java.io.File

fun main() = runBlocking {

    val text = File("src/main/resources/cipher.txt").readText().split(",").map { it.toInt().toChar() }.toCharArray().let { String(it) }
    println((text xor "exp").sumOf { it.code })

    ('a'..'z')
        .asSequence()
        .flatMap {char1 -> ('a'..'z').map { char1.toString() + it } }
        .flatMap {chars -> ('a'..'z').map { chars + it } }
        .map { it to (text xor it) }
        .filter { it.second.contains("the") }
        .filter { it.second.contains(" ") }
        .filter { it.second.contains("and") }
        .forEach(::println)
}