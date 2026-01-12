package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.Player
import kotlinx.coroutines.delay

@Composable
fun BoardComposable() {
    val engine = remember { GameEngine() }

    LaunchedEffect(engine.isBotThinking) {
        if (engine.isBotThinking) {
            delay(600)
            engine.runBot()
        }
    }

    if (engine.showSetupDialog) {
        GameSetupDialogComposable(
            currentVariant = engine.variant,
            onDismiss = {},
            onStart = { variant, mode, difficulty ->
                engine.initializeGame(variant, mode, difficulty)
            }
        )
    }

    if (engine.showWinDialog) {
        GameOverDialogComposable(
            winner = engine.winner,
            history = engine.moveHistory,
            onRestart = {
                engine.showWinDialog = false
                engine.restart()
            },
            onSettings = {
                engine.showWinDialog = false
                engine.showSetupDialog = true
            }
        )
    }

    Column(
        Modifier.fillMaxSize().background(Color(0xFFFAFAFA)).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ToolbarComposable(engine)
        Spacer(Modifier.height(8.dp))
        HandComposable(Player.RED, engine.p2Hand, engine)
        Spacer(Modifier.weight(1f))
        GridComposable(engine)
        Spacer(Modifier.weight(1f))
        HandComposable(Player.BLUE, engine.p1Hand, engine)
        Spacer(Modifier.height(16.dp))
    }
}
