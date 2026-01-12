package io.github.deanalvero.tictactoefamily

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import io.github.deanalvero.tictactoefamily.ui.BoardComposable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        BoardComposable()
    }
}
