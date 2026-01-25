package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.engine.MoveValidator
import io.github.deanalvero.tictactoefamily.engine.WinChecker
import io.github.deanalvero.tictactoefamily.model.*

open class NLevelBestScoreBot(
    private val maxDepth: Int,
    private val tiebreakerRule: TiebreakerRule
) : BotStrategy {
    private val winChecker = WinChecker()

    override fun decideMove(board: List<List<Cell>>, hands: Map<Player, List<Piece>>, botPlayer: Player): MoveAction? {
        val (bestMove, _) = bestMove(
            board = board,
            hands = hands,
            depth = 0,
            blueScore = Int.MIN_VALUE,
            redScore = Int.MAX_VALUE,
            isBluePlayer = botPlayer == Player.BLUE,
            lastMover = if (botPlayer == Player.BLUE) Player.RED else Player.BLUE
        )
        return bestMove
    }

    private fun bestMove(
        board: List<List<Cell>>,
        hands: Map<Player, List<Piece>>,
        depth: Int,
        blueScore: Int,
        redScore: Int,
        isBluePlayer: Boolean,
        lastMover: Player
    ): Pair<MoveAction?, Int> {
        val winner = winner(board, lastMover)
        if (winner == Player.BLUE) return null to (10000 - depth)
        if (winner == Player.RED) return null to (-10000 + depth)
        if (depth >= maxDepth) return null to evaluateBoard(board, hands)

        val currentPlayer = if (isBluePlayer) Player.BLUE else Player.RED
        val moves = BotUtils.generateValidMoves(MoveValidator, board, hands[currentPlayer] ?: emptyList(), currentPlayer)

        if (moves.isEmpty()) return null to 0

        var bestMove: MoveAction? = null
        var currentBlueScore = blueScore
        var currentRedScore = redScore

        if (isBluePlayer) {
            var maxBlueScore = Int.MIN_VALUE
            for (move in moves) {
                val (nextBoard, nextHands) = simulateMove(board, hands, Player.BLUE, move)
                val (_, score) = bestMove(
                    board = nextBoard,
                    hands = nextHands,
                    depth = depth + 1,
                    blueScore = currentBlueScore,
                    redScore = currentRedScore,
                    isBluePlayer = false,
                    lastMover = Player.BLUE
                )
                if (score > maxBlueScore) {
                    maxBlueScore = score
                    bestMove = move
                }
                currentBlueScore = maxOf(currentBlueScore, score)
                if (currentRedScore <= currentBlueScore) break
            }
            return bestMove to maxBlueScore
        } else {
            var minRedScore = Int.MAX_VALUE
            for (move in moves) {
                val (nextBoard, nextHands) = simulateMove(board, hands, Player.RED, move)
                val (_, score) = bestMove(
                    board = nextBoard,
                    hands = nextHands,
                    depth = depth + 1,
                    blueScore = currentBlueScore,
                    redScore = currentRedScore,
                    isBluePlayer = true,
                    lastMover = Player.RED
                )

                if (score < minRedScore) {
                    minRedScore = score
                    bestMove = move
                }
                currentRedScore = minOf(currentRedScore, score)
                if (currentRedScore <= currentBlueScore) break
            }
            return bestMove to minRedScore
        }
    }

    private fun evaluateBoard(board: List<List<Cell>>, hands: Map<Player, List<Piece>>): Int {
        var score = 0

        val blueThreats = countThreats(board, Player.BLUE, hands[Player.BLUE] ?: emptyList())
        val redThreats = countThreats(board, Player.RED, hands[Player.RED] ?: emptyList())
        score += (blueThreats * 1000) - (redThreats * 2000)

        for (r in 0..2) {
            for (c in 0..2) {
                val top = board[r][c].topPiece ?: continue
                val value = (5 - top.rank) * 20 + (if (r == 1 && c == 1) 50 else 0)
                if (top.owner == Player.BLUE) score += value else score -= value
            }
        }
        return score
    }

    private fun countThreats(board: List<List<Cell>>, player: Player, hand: List<Piece>): Int {
        var threats = 0
        val lines = getLines(board)
        val strongestPiece = hand.minByOrNull { it.rank }

        for (line in lines) {
            val ownedCount = line.count { it.topPiece?.owner == player }
            if (ownedCount == 2) {
                val targetCell = line.firstOrNull { it.topPiece?.owner != player }
                if (targetCell?.topPiece == null || (strongestPiece != null && targetCell.topPiece!!.rank > strongestPiece.rank)) {
                    threats++
                }
            }
        }
        return threats
    }

    private fun getLines(board: List<List<Cell>>) = listOf(
        board[0], board[1], board[2],
        listOf(board[0][0], board[1][0], board[2][0]),
        listOf(board[0][1], board[1][1], board[2][1]),
        listOf(board[0][2], board[1][2], board[2][2]),
        listOf(board[0][0], board[1][1], board[2][2]),
        listOf(board[0][2], board[1][1], board[2][0])
    )

    private fun simulateMove(
        board: List<List<Cell>>,
        hands: Map<Player, List<Piece>>,
        player: Player,
        move: MoveAction
    ): Pair<List<List<Cell>>, Map<Player, List<Piece>>> {
        val nextBoard = board.map { row -> row.map { it.copy(pieces = it.pieces.toList()) }.toMutableList() }
        val nextHands = hands.mapValues { it.value.toMutableList() }

        val piece = when (val source = move.source) {
            is MoveSource.Hand -> nextHands[player]!!.removeAt(source.pieceIndex)
            is MoveSource.Board -> {
                val cell = nextBoard[source.row][source.col]
                val p = cell.topPiece!!
                nextBoard[source.row][source.col] = cell.copy(pieces = cell.pieces.dropLast(1))
                p
            }
        }

        val targetCell = nextBoard[move.targetRow][move.targetCol]
        nextBoard[move.targetRow][move.targetCol] = targetCell.copy(pieces = targetCell.pieces + piece)

        return nextBoard to nextHands.mapValues { it.value.toList() }
    }

    private fun winner(board: List<List<Cell>>, initiator: Player): Player? {
        val winners = winChecker.check(board)
        if (winners.isEmpty()) return null
        if (winners.size == 1) return winners.first()

        return if (tiebreakerRule == TiebreakerRule.INITIATIVE) {
            initiator
        } else {
            if (initiator == Player.BLUE) Player.RED else Player.BLUE
        }
    }
}