package de.olk90.codeTextField.view

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import de.olk90.codeTextField.logic.formatText

@Composable
fun CodeTextField(
    modifier: Modifier,
    content: MutableState<TextFieldValue>
) {
    fun updateContent(): (TextFieldValue) -> Unit = {
        formatText(content, it)
    }

    TextField(
        value = content.value,
        onValueChange = updateContent(),
        modifier = modifier,
        textStyle = MaterialTheme.typography.body1.copy(fontFamily = FontFamily.Monospace),
        maxLines = Int.MAX_VALUE
    )
}