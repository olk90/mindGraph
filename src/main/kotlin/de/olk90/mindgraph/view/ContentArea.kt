package de.olk90.mindgraph.de.olk90.mindgraph.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import de.olk90.mindgraph.view.GraphStreamPanel
import org.graphstream.graph.Graph

@Composable
fun ContentArea(
    content: MutableState<String>,
    graphState: MutableState<Graph?>,
    isSaveDialogOpen: MutableState<Boolean>,
    isLoadDialogOpen: MutableState<Boolean>
) {
    Row {
        // Left column (text input)
        TextField(
            value = content.value,
            onValueChange = { content.value = it },
            modifier = Modifier.weight(.3f).fillMaxHeight(),
            textStyle = MaterialTheme.typography.body1.copy(fontFamily = FontFamily.Monospace),
            maxLines = Int.MAX_VALUE
        )

        // Right column (graph visualization)
        Box(modifier = Modifier.weight(.7f)) {
            GraphStreamPanel(content, graphState, isSaveDialogOpen, isLoadDialogOpen)
        }
    }
}

