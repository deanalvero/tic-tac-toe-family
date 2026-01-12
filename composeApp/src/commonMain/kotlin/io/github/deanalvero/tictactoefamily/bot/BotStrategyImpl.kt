package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.Difficulty
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

class BotStrategyImpl(difficulty: Difficulty) : BotStrategy {
    val bot: BotStrategy = when (difficulty) {
        Difficulty.EASY -> EasyBot()
        Difficulty.MEDIUM -> MediumBot()
    }

    override fun decideMove(board: List<List<Cell>>, hand: List<Piece>, botPlayer: Player): MoveAction? {
        return bot.decideMove(board, hand, botPlayer)
    }
}
