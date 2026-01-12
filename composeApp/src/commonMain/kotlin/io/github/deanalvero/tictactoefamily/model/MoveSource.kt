package io.github.deanalvero.tictactoefamily.model

sealed class MoveSource {
    data class Hand(val player: Player, val pieceIndex: Int) : MoveSource()
    data class Board(val row: Int, val col: Int) : MoveSource()
}
