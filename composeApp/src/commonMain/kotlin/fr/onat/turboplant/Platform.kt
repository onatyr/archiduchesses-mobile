package fr.onat.turboplant

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform