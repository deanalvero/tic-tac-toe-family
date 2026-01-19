package io.github.deanalvero.tictactoefamily.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.deanalvero.tictactoefamily.engine.GameEngine

class GameViewModel : ViewModel() {
    val engine = GameEngine()

    companion object {
        val Factory = viewModelFactory {
            initializer { GameViewModel() }
        }
    }
}