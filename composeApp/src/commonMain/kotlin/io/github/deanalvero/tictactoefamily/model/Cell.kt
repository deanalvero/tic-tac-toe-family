package io.github.deanalvero.tictactoefamily.model

data class Cell(val pieces: List<Piece> = emptyList()) {
    val topPiece: Piece? get() = pieces.lastOrNull()
}
