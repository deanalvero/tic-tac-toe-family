package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.GameMode
import io.github.deanalvero.tictactoefamily.model.Player

@Composable
fun GameOverDialogComposable(
    engine: GameEngine,
    onRestart: () -> Unit,
    onSettings: () -> Unit
) {
    val winner = engine.winner
    val history = engine.moveHistory

    Dialog(onDismissRequest = {}) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().heightIn(max = 500.dp)
        ) {
            Column(Modifier.padding(24.dp)) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (winner != null) "${winner.label} Wins!" else "Game Over",
                        style = MaterialTheme.typography.headlineMedium,
                        color = winner?.color ?: Color.Black,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text("Game Mode: ${engine.gameMode.text}")
                    if (engine.gameMode == GameMode.COMPUTER) {
                        Text("Your Side: ${Player.BLUE.label}")
                        Text("Difficulty: ${engine.difficulty.text}")
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(vertical = 8.dp)
                    ) {
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
                            Row(Modifier.padding(vertical = 4.dp)) {
                                Text("${index + 1}. ", color = Color.Gray, fontSize = 14.sp)
                                Text(record.toString(), fontSize = 14.sp, color = Color.DarkGray)
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onSettings, modifier = Modifier.weight(1f)) {
                        Text("Setup")
                    }
                    Button(onClick = onRestart, modifier = Modifier.weight(1f)) {
                        Text("Restart")
                    }
                }
            }
        }
    }
}