package io.github.deanalvero.tictactoefamily.ui

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AboutComposable(
    onDismiss: () -> Unit,
    onOk: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onOk) {
                Text("OK")
            }
        },
        title = { Text("About") },
        text = {
            SelectionContainer {
                Text("Tic Tac Toe Family by Dean Vernon Alvero. The source code can be found at https://github.com/deanalvero/tic-tac-toe-family.")
            }
        }
    )
}