package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.engine.MoveValidator
import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

class MediumBot : BotStrategy {
    override fun decideMove(board: List<List<Cell>>, hand: List<Piece>, botPlayer: Player): MoveAction? {
        val moves = BotUtils.generateValidMoves(MoveValidator, board, hand, botPlayer)
        if (moves.isEmpty()) return null

        return moves.maxByOrNull { m ->
            var score = 0
            val targetCell = board[m.targetRow][m.targetCol]

            if (targetCell.topPiece != null && targetCell.topPiece!!.owner != botPlayer) {
                score += (10 - targetCell.topPiece!!.rank) * 2
            }

            if (m.targetRow == 1 && m.targetCol == 1) score += 5

            score + (0..3).random()
        } ?: moves.random()
    }
}