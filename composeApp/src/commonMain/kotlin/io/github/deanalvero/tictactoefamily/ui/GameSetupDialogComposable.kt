package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import io.github.deanalvero.tictactoefamily.model.Difficulty
import io.github.deanalvero.tictactoefamily.model.GameMode
import io.github.deanalvero.tictactoefamily.model.GameVariant
import io.github.deanalvero.tictactoefamily.model.Player
import io.github.deanalvero.tictactoefamily.model.TiebreakerRule

@Composable
fun GameSetupDialogComposable(
    variant: GameVariant,
    onDismiss: () -> Unit,
    onStart: (GameVariant, GameMode, Player, Difficulty, TiebreakerRule) -> Unit
) {
    var selectedVariant by remember { mutableStateOf(variant) }
    var selectedMode by remember { mutableStateOf(GameMode.COMPUTER) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.MEDIUM) }
    var selectedSide by remember { mutableStateOf(Player.BLUE) }
    var selectedTiebreakerRule by remember { mutableStateOf(TiebreakerRule.INITIATIVE) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("New Game Setup") },
        text = {
            Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                Text(
                    "Game Mode",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GameMode.entries.forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedMode == it,
                                onClick = { selectedMode = it }
                            )
                            Text(it.text, Modifier.clickable { selectedMode = it })
                        }
                    }
                }
                if (selectedMode == GameMode.COMPUTER) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Your Side",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(
                        Modifier.fillMaxWidth()
                    ) {
                        Player.entries.forEach {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedSide == it,
                                    onClick = { selectedSide = it }
                                )
                                Text(it.label, Modifier.clickable { selectedSide = it })
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Difficulty",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Row(
                        Modifier.fillMaxWidth()
                    ) {
                        Difficulty.entries.forEach {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = selectedDifficulty == it,
                                    onClick = { selectedDifficulty = it }
                                )
                                Text(it.text, Modifier.clickable { selectedDifficulty = it })
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Variant",
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(4.dp))
                GameVariant.entries.forEach { variant ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .selectable(
                                selected = (variant == selectedVariant),
                                onClick = { selectedVariant = variant }
                            )
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = (variant == selectedVariant), onClick = null)
                        Column(Modifier.padding(start = 8.dp)) {
                            Text(
                                variant.title,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                variant.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "Tiebreaker Rule",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(
                    Modifier.fillMaxWidth()
                ) {
                    TiebreakerRule.entries.forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedTiebreakerRule == it,
                                onClick = { selectedTiebreakerRule = it }
                            )
                            Text(it.text, Modifier.clickable { selectedTiebreakerRule = it })
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        },
        confirmButton = {
            Button(onClick = {
                onStart(
                    selectedVariant,
                    selectedMode,
                    selectedSide,
                    selectedDifficulty,
                    selectedTiebreakerRule
                )
            }) {
                Text("Start")
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}
