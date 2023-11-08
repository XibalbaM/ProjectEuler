package fr.xibalba.utils

fun <T : Any?> T.print(format: String = "%value", function: ((T) -> String)? = null): Unit =
    if (function != null) println(function(this))
    else println(format.replace("%value", this.toString()))