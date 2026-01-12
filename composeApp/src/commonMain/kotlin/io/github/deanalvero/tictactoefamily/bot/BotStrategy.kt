package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.MoveAction
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

interface BotStrategy {
    fun decideMove(board: List<List<Cell>>, hand: List<Piece>, botPlayer: Player): MoveAction?
}
