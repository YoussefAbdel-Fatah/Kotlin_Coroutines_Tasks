package org.example

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


suspend fun main(): Unit = coroutineScope {

    val sharedFlow = MutableSharedFlow<Int>()

    launch {
        sharedFlow.collect {
            println("first: $it")
        }
    }

    launch {
        sharedFlow.collect {
            println("second: $it")
        }
    }

    sharedFlow.emit(5)
    sharedFlow.emit(3)


    runBlocking {
        delay(3000)
    }
    println("Done")
}