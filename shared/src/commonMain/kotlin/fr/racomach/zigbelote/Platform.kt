package fr.racomach.zigbelote

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform