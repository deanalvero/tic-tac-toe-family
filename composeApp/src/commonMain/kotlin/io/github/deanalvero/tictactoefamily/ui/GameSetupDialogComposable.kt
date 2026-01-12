package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.window.Dialog
import io.github.deanalvero.tictactoefamily.model.Difficulty
import io.github.deanalvero.tictactoefamily.model.GameMode
import io.github.deanalvero.tictactoefamily.model.GameVariant

@Composable
fun GameSetupDialogComposable(
    currentVariant: GameVariant,
    onDismiss: () -> Unit,
    onStart: (GameVariant, GameMode, Difficulty) -> Unit
) {
    var selectedVariant by remember { mutableStateOf(currentVariant) }
    var selectedMode by remember { mutableStateOf(GameMode.COMPUTER) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.MEDIUM) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth().heightIn(max = 700.dp)
        ) {
            Column(Modifier.padding(24.dp).fillMaxWidth().verticalScroll(rememberScrollState())) {
                Text("New Game Setup", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                HorizontalDivider(Modifier.padding(vertical = 12.dp))

                Text("Game Mode", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    GameMode.entries.forEach {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
                    Text("Difficulty", fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Difficulty.entries.forEach {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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

                Text("Variant", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.primary)
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
                            Text(variant.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                            Text(variant.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        onStart(selectedVariant, selectedMode, selectedDifficulty)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Game")
                }
            }
        }
    }
}