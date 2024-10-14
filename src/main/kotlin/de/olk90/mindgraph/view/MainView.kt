package de.olk90.mindgraph.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

@Composable
fun MainUI() {
    MaterialTheme {
        var content by remember { mutableStateOf("mindmap") }

        Row(Modifier.fillMaxSize()) {
            // Left column
            TextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier.weight(1f).fillMaxHeight(),
                textStyle = MaterialTheme.typography.body1.copy(fontFamily = FontFamily.Monospace),
                maxLines = Int.MAX_VALUE
            )

            Box {
                GraphStreamPanel(content)
            }
        }
    }
}