package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.Difficulty
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player
import io.github.deanalvero.tictactoefamily.model.TiebreakerRule

class BotStrategyImpl(difficulty: Difficulty, tiebreakerRule: TiebreakerRule) : BotStrategy {
    val bot: BotStrategy = when (difficulty) {
        Difficulty.EASY -> EasyBot()
        Difficulty.MEDIUM -> MediumBot()
        Difficulty.HARD -> HardBot(tiebreakerRule = tiebreakerRule)
    }

    override fun decideMove(
        board: List<List<Cell>>,
        hands: Map<Player, List<Piece>>,
        botPlayer: Player
    ): MoveAction? {
        return bot.decideMove(board, hands, botPlayer)
    }
}
