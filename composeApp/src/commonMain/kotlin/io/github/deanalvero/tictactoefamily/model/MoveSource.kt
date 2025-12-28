package io.github.deanalvero.tictactoefamily.model

import io.github.deanalvero.tictactoefamily.Player

sealed class MoveSource {
    data class Hand(val player: Player, val pieceIndex: Int) : MoveSource()
    data class Board(val row: Int, val col: Int) : MoveSource()
}
