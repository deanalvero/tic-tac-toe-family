package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.MoveSource
import io.github.deanalvero.tictactoefamily.model.Piece
import io.github.deanalvero.tictactoefamily.model.Player

@Composable
fun HandComposable(
    player: Player,
    hand: List<Piece>,
    engine: GameEngine,
    isVertical: Boolean = false
) {
    val isCurrentTurn = engine.currentPlayer == player
    val backgroundColor = player.color.copy(alpha = 0.1f)

    if (isVertical) {
        Column(
            Modifier
                .fillMaxHeight()
                .width(75.dp)
                .then(
                    if (isCurrentTurn) {
                        Modifier.border(2.dp, player.color, RoundedCornerShape(12.dp))
                    } else {
                        Modifier
                    }
                )
                .background(backgroundColor, RoundedCornerShape(12.dp))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
        ) {
            HandItemsComposable(
                player = player,
                hand = hand,
                engine = engine
            )
        }
    } else {
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
            HandItemsComposable(
                player = player,
                hand = hand,
                engine = engine
            )
        }
    }
}

@Composable
private fun HandItemsComposable(
    player: Player,
    hand: List<Piece>,
    engine: GameEngine
) {
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