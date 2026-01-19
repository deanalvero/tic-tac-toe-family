package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.model.Player
import kotlinx.coroutines.delay

@Composable
fun BoardComposable(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel
) {
    val engine = viewModel.engine

    LaunchedEffect(engine.isBotThinking) {
        if (engine.isBotThinking) {
            delay(600)
            engine.runBot()
        }
    }

    if (engine.showSetupDialog) {
        GameSetupDialogComposable(
            variant = engine.variant,
            onDismiss = {},
            onStart = { variant, mode, side, difficulty ->
                engine.initializeGame(variant, mode, side, difficulty)
            }
        )
    }

    if (engine.showWinDialog) {
        GameOverDialogComposable(
            engine = engine,
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

    BoxWithConstraints(
        modifier =  modifier.background(Color(0xFFFAFAFA)).padding(16.dp)
    ) {
        val isVertical = maxWidth > maxHeight
        if (isVertical) {
            Row (
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                HandComposable(player = Player.RED, hand = engine.p2Hand, engine = engine, isVertical = isVertical)
                Spacer(Modifier.width(8.dp))
                GridComposable(engine)
                Spacer(Modifier.width(8.dp))
                HandComposable(player = Player.BLUE, hand = engine.p1Hand, engine = engine, isVertical = isVertical)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HandComposable(player = Player.RED, hand = engine.p2Hand, engine = engine, isVertical = isVertical)
                Spacer(Modifier.height(8.dp))
                GridComposable(engine)
                Spacer(Modifier.height(8.dp))
                HandComposable(player = Player.BLUE, hand = engine.p1Hand, engine = engine, isVertical = isVertical)
            }
        }
    }
}
