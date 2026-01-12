package io.github.deanalvero.tictactoefamily.model

enum class GameVariant(val title: String, val description: String, val rankCounts: List<Int>) {
    STANDARD("Standard", "3 Ranks, 2 of each.", listOf(2, 2, 2)),
    SINGLETON("Singleton", "4 Ranks, 1 of each.", listOf(1, 1, 1, 1)),
    KING("King", "1 Large, 2 Med, 3 Small.", listOf(1, 2, 3)),
    BINARY("Binary", "2 Ranks, 3 of each.", listOf(3, 3)),
    CHAOS("Chaos", "3 Ranks, 3 of each.", listOf(3, 3, 3))
}
