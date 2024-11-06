package de.olk90.codeTextField.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import de.olk90.codeTextField.logic.formatText

@Composable
fun CodeTextField(
    modifier: Modifier,
    content: MutableState<TextFieldValue>
) {
    fun updateContent(): (TextFieldValue) -> Unit = {
        formatText(content, it)
    }

    var lineTops by remember { mutableStateOf(emptyArray<Float>()) }

    Row {
        if (lineTops.isNotEmpty()) {
            Box(modifier = Modifier.padding(horizontal = 4.dp)) {
                lineTops.forEachIndexed { index, top ->
                    Text(
                        modifier = Modifier.offset(
                            y = with(LocalDensity.current) { top.toDp() }), text = (index + 1).toString()
                    )
                }
            }
        }
        BasicTextField(
            value = content.value,
            onValueChange = updateContent(),
            onTextLayout = { result ->
                lineTops = Array(result.lineCount) { result.getLineTop(it) }
            },
            modifier = modifier,
            textStyle = MaterialTheme.typography.body1.copy(fontFamily = FontFamily.Monospace),
            maxLines = Int.MAX_VALUE
        )
    }

}