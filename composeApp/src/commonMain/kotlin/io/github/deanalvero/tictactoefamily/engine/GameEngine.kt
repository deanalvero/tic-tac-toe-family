package io.github.deanalvero.tictactoefamily.engine

import androidx.compose.animation.core.copy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.github.deanalvero.tictactoefamily.bot.BotStrategy
import io.github.deanalvero.tictactoefamily.bot.BotStrategyImpl
import io.github.deanalvero.tictactoefamily.model.Cell
import io.github.deanalvero.tictactoefamily.model.Difficulty
import io.github.deanalvero.tictactoefamily.model.GameMode
import io.github.deanalvero.tictactoefamily.model.GameVariant
import io.github.deanalvero.tictactoefamily.model.MoveRecord
import io.github.deanalvero.tictactoefamily.model.MoveSource
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

class GameEngine(
    private val validator: MoveValidator = MoveValidator,
    private val winChecker: WinChecker = WinChecker(),
) {
    var board = mutableStateListOf<SnapshotStateList<Cell>>()
    var p1Hand = mutableStateListOf<Piece>()
    var p2Hand = mutableStateListOf<Piece>()
    var currentPlayer by mutableStateOf(Player.BLUE)
    var winner by mutableStateOf<Player?>(null)

    var moveHistory = mutableStateListOf<MoveRecord>()

    var variant by mutableStateOf(GameVariant.STANDARD)
    var gameMode by mutableStateOf(GameMode.COMPUTER)
    var difficulty by mutableStateOf(Difficulty.MEDIUM)
    var showSetupDialog by mutableStateOf(true)
    var showWinDialog by mutableStateOf(false)

    private var bot: BotStrategy = BotStrategyImpl(difficulty)

    var selectedSource by mutableStateOf<MoveSource?>(null)
    var validMoveHints = mutableStateListOf<Pair<Int, Int>>()
    var isBotThinking by mutableStateOf(false)

    fun initializeGame(newVariant: GameVariant, newMode: GameMode, newDifficulty: Difficulty) {
        variant = newVariant
        gameMode = newMode
        difficulty = newDifficulty
        bot = BotStrategyImpl(newDifficulty)

        showSetupDialog = false
        restart()
    }

    fun restart() {
        board.clear()
        repeat(3) {
            val row = mutableStateListOf<Cell>()
            repeat(3) { row.add(Cell()) }
            board.add(row)
        }

        p1Hand.clear()
        p1Hand.addAll(generateHand(Player.BLUE))
        p2Hand.clear()
        p2Hand.addAll(generateHand(Player.RED))
        moveHistory.clear()

        currentPlayer = Player.BLUE
        winner = null
        showWinDialog = false
        isBotThinking = false
        clearSelection()
    }

    private fun generateHand(player: Player): List<Piece> {
        val pieces = mutableListOf<Piece>()
        variant.rankCounts.forEachIndexed { index, count ->
            val rank = index + 1
            repeat(count) { i ->
                pieces.add(Piece("${player.name}-R$rank-$i", player, rank))
            }
        }
        return pieces.sortedBy { it.rank }
    }

    fun handleInteraction(source: MoveSource) {
        if (winner != null || isBotThinking || showSetupDialog || showWinDialog) return

        if (selectedSource != null && source is MoveSource.Board) {
            val (r, c) = source
            if (validMoveHints.contains(r to c)) {
                executeMove(selectedSource!!, r, c)
                return
            }
        }

        val piece = getPiece(source)
        if (piece?.owner == currentPlayer) {
            selectedSource = source
            updateHints(piece, source)
        } else {
            clearSelection()
        }
    }

    fun handleCellClick(r: Int, c: Int) = handleInteraction(MoveSource.Board(r, c))

    private fun updateHints(piece: Piece, source: MoveSource) {
        validMoveHints.clear()
        for (r in board.indices) for (c in board.indices) {
            if (validator.isValid(board, piece, source, r, c)) validMoveHints.add(r to c)
        }
    }

    private fun executeMove(source: MoveSource, tr: Int, tc: Int) {
        val p = getPiece(source) ?: return

        val fromStr = when (source) {
            is MoveSource.Hand -> "Hand"
            is MoveSource.Board -> "(${source.row + 1},${source.col + 1})"
        }
        val toStr = "(${tr + 1},${tc + 1})"
        moveHistory.add(MoveRecord(p.owner, p.rank, fromStr, toStr))

        when (source) {
            is MoveSource.Hand -> {
                if (source.player == Player.BLUE) p1Hand.removeAt(source.pieceIndex)
                else p2Hand.removeAt(source.pieceIndex)
            }
            is MoveSource.Board -> {
                val cell = board[source.row][source.col]
                board[source.row][source.col] = cell.copy(pieces = cell.pieces.dropLast(1))
            }
        }

        val targetCell = board[tr][tc]
        board[tr][tc] = targetCell.copy(pieces = targetCell.pieces + p)

        clearSelection()
        checkGameState()
    }

    private fun checkGameState() {
        winner = winChecker.check(board)
        if (winner != null) {
            showWinDialog = true
        } else {
            currentPlayer = if (currentPlayer == Player.BLUE) Player.RED else Player.BLUE
            if (gameMode == GameMode.COMPUTER && currentPlayer == Player.RED) {
                isBotThinking = true
            }
        }
    }

    fun runBot() {
        val action = bot.decideMove(
            board = board.map {
                it.toList()
            },
            hands = mapOf(
                Player.BLUE to p1Hand.toList(),
                Player.RED to p2Hand.toList()
            ),
            botPlayer = Player.RED
        )
        if (action != null) executeMove(action.source, action.targetRow, action.targetCol)
        else currentPlayer = Player.BLUE
        isBotThinking = false
    }

    private fun getPiece(s: MoveSource) = when (s) {
        is MoveSource.Hand -> (if (s.player == Player.BLUE) p1Hand else p2Hand).getOrNull(s.pieceIndex)
        is MoveSource.Board -> board[s.row][s.col].topPiece
    }

    private fun clearSelection() {
        selectedSource = null
        validMoveHints.clear()
    }
}
