package io.github.deanalvero.tictactoefamily.utils

import io.github.deanalvero.tictactoefamily.model.Piece
import org.jetbrains.compose.resources.DrawableResource
import tictactoefamily.composeapp.generated.resources.Res
import tictactoefamily.composeapp.generated.resources.chess_bishop
import tictactoefamily.composeapp.generated.resources.chess_king
import tictactoefamily.composeapp.generated.resources.chess_pawn
import tictactoefamily.composeapp.generated.resources.chess_queen

fun Piece.getIcon(): DrawableResource = when (rank) {
    1 -> Res.drawable.chess_king
    2 -> Res.drawable.chess_queen
    3 -> Res.drawable.chess_bishop
    else -> Res.drawable.chess_pawn
}
