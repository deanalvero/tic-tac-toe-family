package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.engine.MoveValidator
import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

class EasyBot : BotStrategy {
    override fun decideMove(
        board: List<List<Cell>>,
        hand: List<Piece>,
        botPlayer: Player
    ): MoveAction? {
        val moves = BotUtils.generateValidMoves(MoveValidator, board, hand, botPlayer)
        if (moves.isEmpty()) return null
        return moves.random()
    }
}