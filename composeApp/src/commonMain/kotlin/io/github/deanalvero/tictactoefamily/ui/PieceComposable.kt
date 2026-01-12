package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.utils.getIcon
import org.jetbrains.compose.resources.painterResource

@Composable
fun PieceComposable(piece: Piece, isSelected: Boolean, size: Dp, onClick: (() -> Unit)? = null) {
    val scale by animateFloatAsState(if (isSelected) 1.2f else 1f)
    val rankSizeMod = 1f - ((piece.rank - 1) * 0.12f)

    Box(
        Modifier
            .size(size)
            .scale(scale)
            .let { if (onClick != null) it.clickable { onClick() } else it },
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(piece.owner.color)
                .border(2.dp, Color.White, CircleShape)
        )
        Image(
            painter = painterResource(piece.getIcon()),
            contentDescription = "Rank ${piece.rank}",
            modifier = Modifier
                .fillMaxSize(rankSizeMod * 0.7f)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}