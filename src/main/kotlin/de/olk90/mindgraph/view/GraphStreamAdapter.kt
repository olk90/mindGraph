package de.olk90.mindgraph.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.olk90.mindgraph.logic.renderMermaidMindMap
import org.graphstream.graph.Graph
import org.graphstream.ui.swing.SwingGraphRenderer
import org.graphstream.ui.swing_viewer.SwingViewer
import org.graphstream.ui.view.View
import org.graphstream.ui.view.Viewer
import org.graphstream.ui.view.Viewer.CloseFramePolicy
import java.awt.Component

@Composable
fun GraphStreamPanel(
    content: MutableState<TextFieldValue>,
    graphState: MutableState<Graph?>,
    isSaveDialogOpen: MutableState<Boolean>,
    isLoadDialogOpen: MutableState<Boolean>
) {
    val view = getView(content.value.text, graphState)

    if (!(isSaveDialogOpen.value || isLoadDialogOpen.value)) {
        SwingPanel(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .fillMaxHeight()
                .animateContentSize(), // Add smooth animation for size changes
            factory = { view as Component }
        )
    } else {
        // Render an empty box with no size when invisible
        Box(modifier = Modifier.size(0.dp))
    }
}

private fun getView(text: String, graphState: MutableState<Graph?>): View {
    val graph: Graph = renderMermaidMindMap(text)
    graphState.value = graph

    val viewer = SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD)
    viewer.closeFramePolicy = CloseFramePolicy.EXIT

    // Show the viewer window
    viewer.enableAutoLayout()

    val view = viewer.addView("graphView", SwingGraphRenderer(), false)
    return view
}