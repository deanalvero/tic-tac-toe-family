package io.github.deanalvero.tictactoefamily

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Tic Tac Toe Family",
    ) {
        App()
    }
}