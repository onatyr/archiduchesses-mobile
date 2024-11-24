package fr.onat.turboplant.presentation

import fr.onat.turboplant.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}