package io.github.deanalvero.tictactoefamily.engine

import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.MoveSource
import io.github.deanalvero.tictactoefamily.model.Piece

object MoveValidator {
    fun isValid(board: List<List<Cell>>, piece: Piece, source: MoveSource, row: Int, col: Int): Boolean {
        if (row !in board.indices || col !in board[row].indices) {
            return false
        }
        if (source is MoveSource.Board && source.row == row && source.col == col) {
            return false
        }

        val targetTop = board[row][col].topPiece
        return targetTop == null || piece.rank < targetTop.rank
    }
}