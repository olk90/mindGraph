package de.olk90.mindgraph.logic

import org.graphstream.graph.Graph
import org.graphstream.graph.Node
import org.graphstream.graph.implementations.SingleGraph

const val ROOT_ID = "0"

fun renderMermaidMindMap(content: String): Graph {
    System.setProperty("org.graphstream.ui", "swing")

    // Parse the Mermaid mind map content
    val lines = content.lines().filter { it.isNotEmpty() }
    if (lines.isEmpty() || !lines[0].trim().startsWith("mindmap")) {
        throw IllegalArgumentException("Invalid Mermaid mind map format.")
    }

    // Create a new GraphStream graph
    val graph: Graph = SingleGraph("MindMap")

    if (lines.size > 1) {
        val rootLabel = lines[1].trim()
        val root = graph.addNode(ROOT_ID)
        root.setAttribute("ui.class", "root")
        root.setAttribute("ui.label", rootLabel)

        val nodeMap = mutableMapOf<Node, MutableList<Node>>()

        lines.drop(2).forEachIndexed { idx, str ->

            val offsetIdx = idx + 2
            val nodeLabel = str.trim()
            val nodeId = graph.nodes().count()

            val node = graph.addNode(nodeId.toString())
            node.setAttribute("ui.label", nodeLabel)
            node.setAttribute("ui.class", "leaf")

            val predecessorLine = lines[offsetIdx - 1]
            val predecessorLevel = getLevel(predecessorLine)
            val predecessorId = nodeId - 1
            val predecessorNode = graph.getNode(predecessorId.toString())
            val level = getLevel(str)

            when {
                level > predecessorLevel -> {
                    appendNode(nodeMap, predecessorNode, node)
                    setUiClass(predecessorNode, "middle")
                    graph.addEdge("${predecessorNode.id}-$nodeLabel", predecessorNode, node)
                }

                level == predecessorLevel -> {
                    val parentNode = findKeyContainingNode(nodeMap, predecessorNode)
                    appendNode(nodeMap, parentNode, node)
                    graph.addEdge("${parentNode.id}-${node.id}", parentNode, node)
                }

                else -> {
                    val siblingNode = findKeyContainingNode(nodeMap, predecessorNode)
                    val parentNode = findKeyContainingNode(nodeMap, siblingNode)
                    setUiClass(parentNode, "middle")
                    appendNode(nodeMap, parentNode, node)
                    graph.addEdge("${parentNode.id}-${node.id}", parentNode, node)
                }
            }
        }
    }

    // Set attributes for visualization
    val css =
        ::renderMermaidMindMap.javaClass.classLoader
            .getResourceAsStream("graph.css")!!
            .bufferedReader()
            .useLines { it.joinToString("\n") }
    graph.setAttribute("ui.stylesheet", css)
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
    return targetNode
}

fun setUiClass(node: Node, uiClass: String) {
    if (node.id != ROOT_ID) {
        node.setAttribute("ui.class", uiClass)
    }
}