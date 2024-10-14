package de.olk90.mindgraph.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import de.olk90.filechooser.actions.FileChooserButton
import de.olk90.filechooser.view.FileChooser
import de.olk90.filechooser.view.FileChooserMode
import de.olk90.filechooser.view.defaultFilter

@Composable
fun MainUI() {
    var content by remember { mutableStateOf("mindmap") }
    val isFileChooserOpen = remember { mutableStateOf(false) }
    val filePath = remember { mutableStateOf(System.getProperty("user.home")) }

    MaterialTheme {

        Row(Modifier.fillMaxSize()) {
            // Icon button bar
            Column(
                modifier = Modifier
                    .width(50.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FileChooserButton(isFileChooserOpen, icon = Icons.Default.Save)
            }

            // Divider between icon bar and content
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

            // Main content area
            Row(Modifier.weight(1f)) {
                // Left column (text input)
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier.weight(.3f).fillMaxHeight(),
                    textStyle = MaterialTheme.typography.body1.copy(fontFamily = FontFamily.Monospace),
                    maxLines = Int.MAX_VALUE
                )

                // Right column (graph visualization)
                Box(modifier = Modifier.weight(.7f)) {
                    GraphStreamPanel(content)
                }
            }
        }
    }

    if (isFileChooserOpen.value) {
        FileChooser(isFileChooserOpen, filePath, listOf(defaultFilter), FileChooserMode.FILE)
    }
}