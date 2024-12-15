package de.olk90.filechooser.logic

import java.io.File

fun File.isSvgImage(): Boolean {
    return extension.equals("svg", ignoreCase = true) &&
            inputStream().use { stream ->
                stream.bufferedReader().use { reader ->
                    reader.lineSequence()
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }
                        .any { line ->
                            line.startsWith("<svg", ignoreCase = true) ||
                                    line.contains("<svg", ignoreCase = true)
                        }
                }
            }
}

fun File.isGraphML(): Boolean {
    return extension.equals("graphml", ignoreCase = true) &&
            inputStream().use { stream ->
                val buffer = ByteArray(1024)
                val bytesRead = stream.read(buffer)
                if (bytesRead > 0) {
                    val content = String(buffer, 0, bytesRead)
                    content.contains("<graphml") && content.contains("http://graphml.graphdrawing.org/xmlns")
                } else {
                    false
                }
            }
}


fun File.isPngImage(): Boolean {
    return extension.equals("png", ignoreCase = true) &&
            inputStream().use { it.readNBytes(8).contentEquals(PNG_SIGNATURE) }
}

internal val PNG_SIGNATURE = byteArrayOf(137.toByte(), 80, 78, 71, 13, 10, 26, 10)

internal const val MINIMAL_SVG_CONTENT = """<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="100" height="100">
</svg>
"""

internal const val MINIMAL_GRAPHML_CONTENT = """<?xml version="1.0" encoding="UTF-8"?>
<graphml xmlns="http://graphml.graphdrawing.org/xmlns">
  <graph id="G" edgedefault="undirected">
  </graph>
</graphml>
"""
