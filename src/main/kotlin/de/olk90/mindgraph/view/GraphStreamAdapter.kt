package de.olk90.de.olk90.mindgraph.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import de.olk90.mindgraph.logic.renderMermaidMindMap
import org.graphstream.graph.Graph
import org.graphstream.ui.swing.SwingGraphRenderer
import org.graphstream.ui.swing_viewer.SwingViewer
import org.graphstream.ui.view.View
import org.graphstream.ui.view.Viewer
import org.graphstream.ui.view.Viewer.CloseFramePolicy
import java.awt.Component

@Composable
fun GraphStreamPanel() {
    val view = getView()
    SwingPanel(
        modifier = Modifier.fillMaxSize(),
        factory = { view as Component }
    )
}

private fun getView(): View {
    val graph: Graph = renderMermaidMindMap()

    val viewer = SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD)
    viewer.closeFramePolicy = CloseFramePolicy.EXIT

    // Show the viewer window
    viewer.enableAutoLayout()

    val view = viewer.addView("graphView", SwingGraphRenderer(), false)
    return view
}