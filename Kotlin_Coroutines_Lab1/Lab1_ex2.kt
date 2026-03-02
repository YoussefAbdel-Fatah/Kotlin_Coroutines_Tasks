package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *  Create a flow that emits twenty even numbers, every second an emission, and
 * collect the first ten numbers. (Don’t use if-statement or filter, map operator or
 * repeat operator )*/

suspend fun main() {
    CoroutineScope(Dispatchers.Default).launch {
        getEvenNumbers()
            .take(10)
            .collect {
                println("collect: $it")
                delay(100)
            }
    }

    runBlocking { delay(3000) }
}

fun getEvenNumbers() = flow<Int> {
    for (i in 2..40 step 2) {
        println("emit: $i")
        emit(i)
        delay(100)
    }
}