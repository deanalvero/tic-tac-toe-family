package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.model.Cell

@Composable
fun SquareComposable(
    cell: Cell,
    isHint: Boolean,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(0.5.dp, Color(0xFFEEEEEE))
            .background(Color.White)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isHint) Box(Modifier.fillMaxSize().background(Color(0xFF66BB6A).copy(0.2f)))

        cell.topPiece?.let {
            PieceComposable(it, isSelected, 100.dp)
        }

        if (isHint && cell.topPiece == null) {
            Box(Modifier.size(16.dp).clip(CircleShape).background(Color(0xFF66BB6A).copy(0.6f)))
        }
    }
}