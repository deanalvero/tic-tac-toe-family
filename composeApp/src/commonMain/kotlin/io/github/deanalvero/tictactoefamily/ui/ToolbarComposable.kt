package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.github.deanalvero.tictactoefamily.engine.GameEngine

@Composable
fun ToolbarComposable(engine: GameEngine) {
    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween, Alignment.CenterVertically) {
        Column {
            Text(engine.variant.title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            val status = engine.winner?.let {
                "Winner: ${it.label}!"
            } ?: "${engine.currentPlayer.label}'s Turn"

            Text(
                text = status,
                color = engine.winner?.color ?: Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(
            onClick = {
                engine.showSetupDialog = true
            }
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}
