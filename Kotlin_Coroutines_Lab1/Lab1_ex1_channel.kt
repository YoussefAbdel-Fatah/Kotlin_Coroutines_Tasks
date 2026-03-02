package org.example

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun main() {
    CoroutineScope(Dispatchers.Default).launch {
        val channel = Channel<Int>()

        // Coroutine that sends 0 1 2 3 4  ( 5 items )
        launch {
            repeat(5) {
                channel.send(it)
            }
        }

        // Coroutine that sends 10 11 12 13 14 15 ( 6 items )
        launch {
            (10..15).forEach { item ->
                channel.send(item)
            }
        }

        // Coroutine that receive 11 items
        launch {
            repeat(11) {
                val result = channel.receive()
                println("${Thread.currentThread().name} received $result")
            }
        }

    }

    runBlocking {
        delay(5000)
    }
}