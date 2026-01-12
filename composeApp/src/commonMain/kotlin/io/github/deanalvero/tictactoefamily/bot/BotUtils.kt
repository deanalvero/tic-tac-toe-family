package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.engine.MoveValidator
import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.MoveSource
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

object BotUtils {
    fun generateValidMoves(validator: MoveValidator, board: List<List<Cell>>, hand: List<Piece>, player: Player): List<MoveAction> {
        val moves = mutableListOf<MoveAction>()

        hand.forEachIndexed { index, piece ->
            val source = MoveSource.Hand(player, index)
            for (row in board.indices) {
                for (col in board.indices) {
                    if (validator.isValid(board, piece, source, row, col)) {
                        moves.add(MoveAction(source, row, col))
                    }
                }
            }
        }

        for (row in board.indices) {
            for (col in board.indices) {
                val piece = board[row][col].topPiece ?: continue
                if (piece.owner != player) continue
                val source = MoveSource.Board(row, col)

                for (tr in board.indices) {
                    for (tc in board.indices) {
                        if (validator.isValid(board, piece, source, tr, tc)) {
                            moves.add(MoveAction(source, tr, tc))
                        }
                    }
                }
            }
        }
        return moves
    }
}