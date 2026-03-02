package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun main() {

    CoroutineScope(Dispatchers.Default).launch {
        getNumbers()
            .filter { it > 5 }
            .map { it * 2 }
            .collect { println(it) }
    }


    runBlocking {
        delay(5000)
    }
}

fun getNumbers() = flow<Int> {
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}