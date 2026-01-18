package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.MoveSource
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

@Composable
fun HandComposable(player: Player, hand: List<Piece>, engine: GameEngine) {
    val isCurrentTurn = engine.currentPlayer == player
    val backgroundColor = player.color.copy(alpha = 0.1f)
    Row(
        Modifier
            .fillMaxWidth()
            .height(75.dp)
            .then(
                if (isCurrentTurn) {
                    Modifier.border(2.dp, player.color, RoundedCornerShape(12.dp))
                } else {
                    Modifier
                }
            )
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (hand.isEmpty()) {
            Text("No pieces", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(16.dp))
        } else {
            hand.forEachIndexed { index, piece ->
                val isSelected = engine.selectedSource is MoveSource.Hand &&
                        (engine.selectedSource as MoveSource.Hand).pieceIndex == index &&
                        (engine.selectedSource as MoveSource.Hand).player == player

                Box(Modifier.padding(horizontal = 3.dp)) {
                    PieceComposable(piece, isSelected, 50.dp) {
                        engine.handleInteraction(MoveSource.Hand(player, index))
                    }
                }
            }
        }
    }
}