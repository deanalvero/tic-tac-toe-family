package io.github.deanalvero.tictactoefamily.model

enum class GameVariant(val boardSize: Int, val totalRanks: Int, val piecesPerRank: Int) {
    VARIANT_3X3_3R_3P(3, 3, 2)
}
