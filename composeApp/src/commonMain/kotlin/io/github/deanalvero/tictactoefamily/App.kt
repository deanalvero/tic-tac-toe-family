package io.github.deanalvero.tictactoefamily

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.deanalvero.tictactoefamily.ui.AboutComposable
import io.github.deanalvero.tictactoefamily.ui.BoardComposable
import io.github.deanalvero.tictactoefamily.ui.GameViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(
    viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)
) {
    var isDropdownMenuShown by remember { mutableStateOf(false) }
    var isSourceDialogShown by remember { mutableStateOf(false) }

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Tic Tac Toe Family") },
                    actions = {
                        IconButton(onClick = { isDropdownMenuShown = true }) {
                            Icon(Icons.Default.MoreVert, "More")
                        }
                        DropdownMenu(
                            expanded = isDropdownMenuShown,
                            onDismissRequest = { isDropdownMenuShown = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("About") },
                                onClick = {
                                    isDropdownMenuShown = false
                                    isSourceDialogShown = true
                                }
                            )
                        }
                    }
                )
            }
        ) { padding ->
            BoardComposable(
                modifier = Modifier.padding(padding).fillMaxSize(),
                viewModel = viewModel
            )
            if (isSourceDialogShown) {
                AboutComposable(
                    onDismiss = {
                        isSourceDialogShown = false
                    },
                    onOk = {
                        isSourceDialogShown = false
                    }
                )
            }
        }
    }
}
