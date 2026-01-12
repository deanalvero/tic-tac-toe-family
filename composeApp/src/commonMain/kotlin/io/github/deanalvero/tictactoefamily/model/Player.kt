package io.github.deanalvero.tictactoefamily.model

import androidx.compose.ui.graphics.Color

enum class Player(val color: Color, val label: String) {
    BLUE(Color(0xFF42A5F5), "Blue Player"),
    RED(Color(0xFFEF5350), "Red Player")
}
