package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.GameMode
import io.github.deanalvero.tictactoefamily.model.Piece

@Composable
fun GameOverDialogComposable(
    engine: GameEngine,
    onRestart: () -> Unit,
    onSettings: () -> Unit
) {
    val winner = engine.winner
    val history = engine.moveHistory

    AlertDialog(
        onDismissRequest = onSettings,
        confirmButton = {
            Button(onClick = onRestart) {
                Text("Restart")
            }
        },
        dismissButton = {
            TextButton(onClick = onSettings) {
                Text("Cancel")
            }
        },
        title = {
            Text(if (winner != null) "${winner.label} Wins!" else "Game Over")
        },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                Text("Game Mode: ${engine.gameMode.text}")
                if (engine.gameMode == GameMode.COMPUTER) {
                    Text("Your Side: ${engine.humanPlayer.label}")
                    Text("Difficulty: ${engine.difficulty.text}")
                }
                Text("Tiebreaker Rule: ${engine.tiebreakerRule.text}")
                Box(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .border(1.dp, Color.LightGray,RoundedCornerShape(8.dp))
                        .padding(4.dp)
                ) {
                    Column {
                        engine.board.forEach { row ->
                            Row {
                                row.forEach { cell ->
                                    Box(
                                        Modifier
                                            .size(50.dp)
                                            .border(0.5.dp, Color(0xFFF0F0F0)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        cell.topPiece?.let { piece ->
                                            PieceComposable(
                                                piece,
                                                isSelected = false,
                                                size = 35.dp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (history.isEmpty()) {
                    Text("No moves made.")
                }
                history.forEachIndexed { index, record ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${index + 1}. ", color = Color.Gray, fontSize = 14.sp)
                        PieceComposable(
                            piece = Piece(
                                id = "history_$index",
                                owner = record.player,
                                rank = record.pieceRank
                            ),
                            isSelected = false,
                            size = 24.dp
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(record.toString(), fontSize = 14.sp, color = Color.DarkGray)
                    }
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}