package io.github.deanalvero.tictactoefamily.engine

import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.Player

class WinChecker {
    fun check(board: List<List<Cell>>): Set<Player> {
        val winners = mutableSetOf<Player>()

        val size = 3
        fun owner(r: Int, c: Int) = board[r][c].topPiece?.owner

        for (i in 0 until size) {
            val rowOwner = owner(i, 0)
            if (rowOwner != null && (1 until size).all { owner(i, it) == rowOwner }) {
                winners.add(rowOwner)
            }
            val colOwner = owner(0, i)
            if (colOwner != null && (1 until size).all { owner(it, i) == colOwner }) {
                winners.add(colOwner)
            }
        }
        val diagonal1Owner = owner(0, 0)
        if (diagonal1Owner != null && (1 until size).all { owner(it, it) == diagonal1Owner }) {
            winners.add(diagonal1Owner)
        }
        val diagonal2Owner = owner(0, size - 1)
        if (diagonal2Owner != null && (1 until size).all { owner(it, size - 1 - it) == diagonal2Owner }) {
            winners.add(diagonal2Owner)
        }
        return winners
    }
}
