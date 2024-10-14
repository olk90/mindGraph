package de.olk90.de.olk90.mindgraph.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MainUI() {
    MaterialTheme {
        Column {
            Text("GraphStream in Compose")
            GraphStreamPanel()
        }
    }
}