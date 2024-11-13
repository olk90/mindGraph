package de.olk90.mindgraph.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.olk90.filechooser.logic.isPngImage
import de.olk90.filechooser.view.FileChooser
import de.olk90.filechooser.view.FileChooserMode
import de.olk90.filechooser.view.loadFormats
import de.olk90.filechooser.view.saveFormats
import de.olk90.mindgraph.de.olk90.mindgraph.view.ButtonBar
import de.olk90.mindgraph.de.olk90.mindgraph.view.ContentArea
import org.graphstream.graph.Graph
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy
import org.graphstream.stream.file.FileSinkImages.OutputType
import org.graphstream.stream.file.images.Resolutions
import org.graphstream.ui.swing.util.SwingFileSinkImages
import java.io.File

@Composable
fun MainUI() {
    var content = remember { mutableStateOf(TextFieldValue("mindmap")) }
    val isSaveDialogOpen = remember { mutableStateOf(false) }
    val isLoadDialogOpen = remember { mutableStateOf(false) }

    val graphState: MutableState<Graph?> = remember { mutableStateOf(null) }

    val saveAction = { file: File ->
        if (file.isFile) {
            if (file.isPngImage()) {

                if (graphState.value != null) {
                    val fsi = SwingFileSinkImages(OutputType.png, Resolutions.HD1080)
                    fsi.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE)
                    fsi.writeAll(graphState.value, file.absolutePath)
                }

            } else {
                file.writeText(content.value.text)
            }
        }
    }

    val loadAction = { file: File ->
        if (file.isFile) {
            content.value = TextFieldValue(file.readText())
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
        FileChooser(isSaveDialogOpen, saveFormats, FileChooserMode.FILE, saveAction)
    }
    if (isLoadDialogOpen.value) {
        FileChooser(isLoadDialogOpen, loadFormats, FileChooserMode.FILE, loadAction)
    }
}