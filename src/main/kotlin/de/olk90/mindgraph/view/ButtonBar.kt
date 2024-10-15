package de.olk90.mindgraph.de.olk90.mindgraph.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.olk90.filechooser.actions.FileChooserButton

@Composable
fun ButtonBar(isFileChooserOpen: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .width(50.dp)
            .fillMaxHeight()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FileChooserButton(isFileChooserOpen, icon = Icons.Default.Save)
    }
}