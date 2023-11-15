package fr.xibalba.utils

fun <T : Any?> T.print(format: String = "%value", function: ((T) -> String)? = null) =
    if (function != null) this.also { println(function(this)) }
    else this.also { println(format.replace("%value", this.toString())) }