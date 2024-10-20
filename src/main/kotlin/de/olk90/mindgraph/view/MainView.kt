package de.olk90.mindgraph.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.olk90.filechooser.logic.isPngImage
import de.olk90.filechooser.view.FileChooser
import de.olk90.filechooser.view.FileChooserMode
import de.olk90.filechooser.view.loadFormats
import de.olk90.filechooser.view.saveFormats
import de.olk90.mindgraph.de.olk90.mindgraph.view.ButtonBar
import de.olk90.mindgraph.de.olk90.mindgraph.view.ContentArea
import org.graphstream.graph.Graph
import java.io.File

@Composable
fun MainUI() {
    var content = remember { mutableStateOf("mindmap") }
    val isSaveDialogOpen = remember { mutableStateOf(false) }
    val isLoadDialogOpen = remember { mutableStateOf(false) }
    val filePath = remember { mutableStateOf(System.getProperty("user.home")) }

    val graphState: MutableState<Graph?> = remember { mutableStateOf(null) }

    val saveAction = { file: File ->
        if (file.isFile) {
            if(file.isPngImage()) {
                // TODO use PNG file sink
            } else {
                file.writeText(content.value)
            }
        }
    }

    val loadAction = { file: File ->
        if (file.isFile) {
            content.value = file.readText()
        }
    }

    MaterialTheme {

        Row(Modifier.fillMaxSize()) {
            // Icon button bar
            ButtonBar(isSaveDialogOpen, isLoadDialogOpen)

            // Divider between icon bar and content
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

            // Main content area
            ContentArea(content, graphState, isSaveDialogOpen, isLoadDialogOpen)
        }
    }

    if (isSaveDialogOpen.value) {
        FileChooser(isSaveDialogOpen, filePath, saveFormats, FileChooserMode.FILE, saveAction)
    }
    if (isLoadDialogOpen.value) {
        FileChooser(isLoadDialogOpen, filePath, loadFormats, FileChooserMode.FILE, loadAction)
    }
}