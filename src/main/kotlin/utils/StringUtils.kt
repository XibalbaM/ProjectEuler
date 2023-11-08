package fr.xibalba.utils

fun String.remove(char: Char): String = this.replace(char.toString(), "")
fun String.remove(chars: String): String = this.replace(chars, "")
fun String?.ifNotBlank(block: String.() -> String): String? =
    if (!this.isNullOrBlank()) this.block()
    else this
fun Char.alphabeticalPosition(): Int = this.lowercaseChar().code - 96

fun String.permutations(): List<String> {
    if (this.length == 1) return listOf(this)
    val perms = mutableListOf<String>()
    val toInsert = this[0]
    for (perm in this.remove(toInsert).permutations()) {
        for (i in 0..perm.length) {
            perms.add(perm.substring(0, i) + toInsert + perm.substring(i))
        }
    }
    return perms
}