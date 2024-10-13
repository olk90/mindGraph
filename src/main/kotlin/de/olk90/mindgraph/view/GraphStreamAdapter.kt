package de.olk90.mindgraph.view

import de.olk90.logic.renderMermaidMindMap
import org.graphstream.graph.Graph
import org.graphstream.ui.view.Viewer
import org.graphstream.ui.view.Viewer.CloseFramePolicy


fun displayGraph(): Viewer {
    System.setProperty("org.graphstream.ui", "swing")

    // Create a new graph
    val graph: Graph = renderMermaidMindMap()

    // Create a viewer to display the graph
    val viewer = graph.display()
    viewer.closeFramePolicy = CloseFramePolicy.EXIT

    // Show the viewer window
    viewer.enableAutoLayout()

    return viewer
}