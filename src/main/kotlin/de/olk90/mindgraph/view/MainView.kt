package de.olk90.mindgraph.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.olk90.filechooser.logic.isPngImage
import de.olk90.filechooser.view.FileChooser
import de.olk90.filechooser.view.FileChooserMode
import de.olk90.filechooser.view.mindMapFormats
import de.olk90.mindgraph.de.olk90.mindgraph.view.ButtonBar
import de.olk90.mindgraph.de.olk90.mindgraph.view.ContentArea
import org.graphstream.graph.Graph
import java.io.File

@Composable
fun MainUI() {
    var content = remember { mutableStateOf("mindmap") }
    val isFileChooserOpen = remember { mutableStateOf(false) }
    val filePath = remember { mutableStateOf(System.getProperty("user.home")) }

    val graphState: MutableState<Graph?> = remember { mutableStateOf(null) }

    val action = { file: File ->
        if (file.isFile) {
            if(file.isPngImage()) {
                // TODO use PNG file sink
            } else {
                file.writeText(content.value)
            }
        }
    }

    MaterialTheme {

        Row(Modifier.fillMaxSize()) {
            // Icon button bar
            ButtonBar(isFileChooserOpen)

            // Divider between icon bar and content
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

            // Main content area
            ContentArea(content, graphState, isFileChooserOpen)
        }
    }

    if (isFileChooserOpen.value) {
        FileChooser(isFileChooserOpen, filePath, mindMapFormats, FileChooserMode.FILE, action)
    }
}