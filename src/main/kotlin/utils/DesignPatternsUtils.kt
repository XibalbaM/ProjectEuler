package fr.xibalba.utils

// CREATIONAL PATTERNS

// Prototype pattern
interface Cloneable<T: Cloneable<T>> {
    fun clone(): T
}
open class Registry<T : Cloneable<T>> {

    private val registry = mutableMapOf<String, T>()

    fun addItem(id: String, element: T) {
        registry[id] = element
    }

    fun remove(id: String) {
        registry.remove(id)
    }

    fun get(id: String): T? = registry[id]?.clone()
}

// Builder pattern
interface Builder<T> {

    var instance: T

    fun build(): T = instance

    fun reset(): Builder<T>
}



// STRUCTURAL PATTERNS

//
