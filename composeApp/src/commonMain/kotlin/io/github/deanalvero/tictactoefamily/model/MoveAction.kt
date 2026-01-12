package io.github.deanalvero.tictactoefamily.model

data class MoveAction(val source: MoveSource, val targetRow: Int, val targetCol: Int)
