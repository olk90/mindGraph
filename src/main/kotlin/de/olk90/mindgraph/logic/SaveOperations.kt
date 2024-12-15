package de.olk90.mindgraph.logic

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import de.olk90.filechooser.logic.isGraphML
import de.olk90.filechooser.logic.isPngImage
import de.olk90.filechooser.logic.isSvgImage
import org.graphstream.graph.Graph
import org.graphstream.stream.file.FileSinkGraphML
import org.graphstream.stream.file.FileSinkImages.LayoutPolicy
import org.graphstream.stream.file.FileSinkImages.OutputType
import org.graphstream.stream.file.FileSinkSVG
import org.graphstream.stream.file.images.Resolutions
import org.graphstream.ui.swing.util.SwingFileSinkImages
import java.io.File

fun saveFile(file: File, graph: Graph, content: MutableState<TextFieldValue>) {
    val absolutePath = file.absolutePath
    when {
        file.isPngImage() -> savePng(graph, absolutePath)
        file.isSvgImage() -> saveSvg(graph, absolutePath)
        file.isGraphML() -> saveGraphMl(graph, absolutePath)
        else -> file.writeText(content.value.text)
    }
}

private fun savePng(graph: Graph, absolutePath: String) {
    val fileSink = SwingFileSinkImages(OutputType.png, Resolutions.HD1080)
    fileSink.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE)
    fileSink.writeAll(graph, absolutePath)
}

/**
 * This method is not really usable, since the FileSinkSVG
 * has a known bug: https://github.com/graphstream/gs-core/issues/378
 */
private fun saveSvg(graph: Graph, absolutePath: String) {
    val fileSink = FileSinkSVG()
    fileSink.writeAll(graph, absolutePath)
}

private fun saveGraphMl(graph: Graph, absolutePath: String) {
    val fileSink = FileSinkGraphML()
    fileSink.writeAll(graph, absolutePath)
}
