package de.olk90.mindgraph.de.olk90.mindgraph.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import de.olk90.codeTextField.view.CodeTextField
import de.olk90.mindgraph.view.GraphStreamPanel
import org.graphstream.graph.Graph

@Composable
fun ContentArea(
    content: MutableState<TextFieldValue>,
    graphState: MutableState<Graph?>,
    isSaveDialogOpen: MutableState<Boolean>,
    isLoadDialogOpen: MutableState<Boolean>
) {
    Row {
        // Left column (text input)
        CodeTextField(
            content = content,
            modifier = Modifier.weight(.3f).fillMaxHeight(),
        )

        // Right column (graph visualization)
        Box(modifier = Modifier.weight(.7f)) {
            GraphStreamPanel(content, graphState, isSaveDialogOpen, isLoadDialogOpen)
        }
    }
}

