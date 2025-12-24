package io.github.deanalvero.tictactoefamily

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform