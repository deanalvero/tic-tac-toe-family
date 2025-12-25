package io.github.deanalvero.tictactoefamily.model

sealed class Player(val text: String) {
    object P1 : Player("Player 1")
    object P2 : Player("Player 2")
}
