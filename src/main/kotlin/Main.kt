package de.olk90.mindgraph

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import de.olk90.mindgraph.view.MainUI


fun main() = application {
    val title = "mindGraph"

    Window(onCloseRequest = ::exitApplication, title = title) {
        MainUI()
    }
}

