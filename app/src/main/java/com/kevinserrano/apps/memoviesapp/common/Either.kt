package com.kevinserrano.apps.memoviesapp.common

sealed class Either<out L, out R> {
    data class Left<out L>(val a: L) : Either<L, Nothing>()
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    fun either(funL: (L) -> Any, funR: (R) -> Any): Any = when (this) {
        is Left -> funL(a)
        is Right -> funR(b)
    }
}