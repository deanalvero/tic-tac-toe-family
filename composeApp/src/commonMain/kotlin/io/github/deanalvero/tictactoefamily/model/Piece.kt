package io.github.deanalvero.tictactoefamily.model

data class Piece(
    val id: String,
    val owner: Player,
    val rank: Int
)
