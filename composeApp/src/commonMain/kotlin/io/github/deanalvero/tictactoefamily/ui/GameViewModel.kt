package io.github.deanalvero.tictactoefamily.ui

import androidx.lifecycle.ViewModel
import io.github.deanalvero.tictactoefamily.engine.GameEngine

class GameViewModel : ViewModel() {
    val engine = GameEngine()
}