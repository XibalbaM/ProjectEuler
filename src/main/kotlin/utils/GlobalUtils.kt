package fr.xibalba.utils

fun <T : Any?> T.print(format: String = "%value", function: ((T) -> String)? = null) =
    if (function != null) this.also { println(function(this)) }
    else this.also { println(format.replace("%value", this.toString())) }

fun <T : Any?> T.applyXTimes(times: Int, function: (T) -> T): T {
    var result = this
    repeat(times) { result = function(result) }
    return result
}