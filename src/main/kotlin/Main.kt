package de.olk90

import org.graphstream.graph.Graph
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer.CloseFramePolicy

fun main() {
    System.setProperty("org.graphstream.ui", "swing")

    // Create a new graph
    val graph: Graph = SingleGraph("Tutorial")

    // Add nodes and edges
    graph.addNode("A").setAttribute("ui.label", "Node A")
    graph.addNode("B").setAttribute("ui.label", "Node B")
    graph.addNode("C").setAttribute("ui.label", "Node C")

    graph.addEdge("AB", "A", "B")
    graph.addEdge("AC", "A", "C")

    // Set attributes for visualization
    graph.setAttribute("ui.stylesheet", """
        node { shape: freeplane; fill-color: white; stroke-mode: plain; size-mode: fit; stroke-width: 3px; stroke-color: #333; }
        edge { shape: freeplane; size: 2px; fill-color: #444; }
        """)

    graph.setAttribute("ui.quality")
    graph.setAttribute("ui.antialias")

    // Create a viewer to display the graph
    val viewer = graph.display()
    viewer.setCloseFramePolicy(CloseFramePolicy.EXIT)

    // Show the viewer window
    viewer.enableAutoLayout()
}