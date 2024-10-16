package de.olk90.filechooser.view

class FileExtensionFilter(
    val fileExtension: String,
    private val description: String
) {
    override fun toString(): String {
        return "$description (*.$fileExtension)"
    }
}

val defaultFilter = FileExtensionFilter("*", "All files")

val mindMapFormats = listOf(
    defaultFilter,
    FileExtensionFilter("png", "PNG image"),
    FileExtensionFilter("svg", "Scalable vector graphic"),
    FileExtensionFilter("md", "Markdown file"),
    FileExtensionFilter("txt", "Text file")
)