package de.olk90

import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph
import org.graphstream.ui.view.Viewer.CloseFramePolicy

fun main() {
    System.setProperty("org.graphstream.ui", "swing")

    // Create a new graph
    val graph: Graph = renderMermaidMindMap("")

    // Create a viewer to display the graph
    val viewer = graph.display()
    viewer.setCloseFramePolicy(CloseFramePolicy.EXIT)

    // Show the viewer window
    viewer.enableAutoLayout()
}

fun renderMermaidMindMap(filePath: String): Graph {
    // Read the Mermaid mind map file
//    val content = File(filePath).readText()
    val content = """mindmap
Nutzen und Ziele
	Reduktion von Komplexität
	Erreichtung der Qualitätsanforderungen
		Zuverlässigkeit
		Wartbarkeit
		Änderbarkeit
		Sicherheit
		Energieeffizienz
	Sicherstellung der Erfüllbarkeit funktionaler Anforderungen
	Unterstützung vom Entwurf bis zur Weiterentwicklung
	Vermittlung von Verständnis
	Spezifikation architekturrelevanter Richtlinien
"""

    // Parse the Mermaid mind map content
    val lines = content.lines().filter { it.isNotEmpty() }
    if (lines.isEmpty() || !lines[0].trim().startsWith("mindmap")) {
        throw IllegalArgumentException("Invalid Mermaid mind map format.")
    }

    // Create a new GraphStream graph
    val graph: Graph = SingleGraph("MindMap")

    val rootId = lines[1].trim()
    val root = graph.addNode(rootId)
    root.setAttribute("ui.label", rootId)

    val nodeMap = mutableMapOf<Node, MutableList<Node>>()

    lines.drop(2).forEachIndexed { idx, str ->

        val offsetIdx = idx + 2
        val nodeId = str.trim()
        val node = graph.addNode(nodeId)
        node.setAttribute("ui.label", nodeId)
        val predecessor = lines[offsetIdx - 1]
        val predecessorLevel = getLevel(predecessor)
        val predecessorNode =  graph.getNode(predecessor.trim())
        val level = getLevel(str)

        when {
            level > predecessorLevel -> {
                appendNode(nodeMap, predecessorNode, node)
                graph.addEdge("${predecessorNode.id}-$nodeId", predecessorNode, node)
            }
            level == predecessorLevel -> {
                val parentNode = findKeyContainingNode(nodeMap, predecessorNode)
                appendNode(nodeMap, parentNode, node)
                graph.addEdge("${parentNode.id}-${node.id}", parentNode, node)
            }
            else -> {
                val siblingNode = findKeyContainingNode(nodeMap, predecessorNode)
                val parentNode = findKeyContainingNode(nodeMap, siblingNode)
                appendNode(nodeMap, parentNode, node)
                graph.addEdge("${parentNode.id}-${node.id}", parentNode, node)
            }
        }
    }

    // Set attributes for visualization
    graph.setAttribute(
        "ui.stylesheet", """
        node { shape: freeplane; fill-color: white; stroke-mode: plain; size-mode: fit; }
        edge { shape: freeplane; size: 2px; fill-color: #444; }
        """
    )
    return graph
}

private fun getLevel(string: String): Int {
    return string.takeWhile { it == '\t' }.length
}

fun appendNode(nodeMap: MutableMap<Node, MutableList<Node>>, key: Node, newNode: Node) {
    if (nodeMap.containsKey(key)) {
        nodeMap[key]!!.add(newNode)
    } else {
        nodeMap[key] = mutableListOf(newNode)
    }
}

fun findKeyContainingNode(nodeMap: MutableMap<Node, MutableList<Node>>, targetNode: Node): Node {
    for ((key, nodeList) in nodeMap) {
        if (nodeList.contains(targetNode)) {
            return key
        }
    }
//    throw IllegalArgumentException("Node ${targetNode.id} not found in any list")
    return targetNode
}