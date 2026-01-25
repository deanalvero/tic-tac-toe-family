package io.github.deanalvero.tictactoefamily.model

data class MoveRecord(
    val player: Player,
    val pieceRank: Int,
    val from: String,
    val to: String
) {
    override fun toString(): String = "$from > $to"
}