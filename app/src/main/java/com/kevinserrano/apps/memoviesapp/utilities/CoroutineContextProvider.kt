package com.kevinserrano.apps.memoviesapp.utilities

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val io: CoroutineContext
    val computation: CoroutineContext
    val main: CoroutineContext
}
