package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val stateFlow = MutableStateFlow("")

    launch {
        stateFlow.collect {value ->
            println("value: $value")
        }
    }

    stateFlow.value = "1"
    delay(100)
    stateFlow.emit("2")
    delay(100)
    stateFlow.value = "3"

}