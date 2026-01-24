package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.deanalvero.tictactoefamily.engine.GameEngine
import io.github.deanalvero.tictactoefamily.model.MoveSource

@Composable
fun GridComposable(
    engine: GameEngine,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .padding(4.dp)
    ) {
        engine.board.forEachIndexed { rIndex, row ->
            Row(modifier = Modifier.weight(1f)) {
                row.forEachIndexed { cIndex, cell ->
                    val isHint = engine.validMoveHints.contains(rIndex to cIndex)
                    val isSelected = engine.selectedSource is MoveSource.Board &&
                            (engine.selectedSource as MoveSource.Board).row == rIndex &&
                            (engine.selectedSource as MoveSource.Board).col == cIndex

                    SquareComposable(
                        cell = cell,
                        isHint = isHint,
                        isSelected = isSelected,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    ) {
                        engine.handleCellClick(
                            rIndex,
                            cIndex
                        )
                    }
                }
            }
        }
    }
}