package de.olk90.codeTextField.logic

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlin.text.isWhitespace

fun formatText(textState: MutableState<TextFieldValue>, newValue: TextFieldValue) {
    if (newValue.text.isEmpty()) {
        textState.value = newValue
        return
    }
    if (insertLeadingTabs(textState, newValue)) return
    if (appendClosingBrackets(textState, newValue)) return
    textState.value = newValue
}

private fun insertLeadingTabs(textState: MutableState<TextFieldValue>, newValue: TextFieldValue): Boolean {
    val currentText = newValue.text
    val cursorPosition = newValue.selection.start

    val cursorIndex = maxOf(0, cursorPosition - 1)
    val lastSymbol = currentText[cursorIndex]
    if (currentText.length > textState.value.text.length && lastSymbol == '\n') {
        val lines = currentText.split("\n")

        val currentLineIndex = currentText.countNewlinesBefore(cursorIndex)
        val lastLineIndex = maxOf(0, currentLineIndex - 1)

        val lastLine = lines[lastLineIndex]
        val tabs = countLeadingTabs(lastLine)

        val newLineIndentation = "\t".repeat(tabs) // Add tabs based on last line
        val updatedText = currentText.insertStringAt(newLineIndentation, cursorPosition)

        textState.value = TextFieldValue(updatedText, selection = TextRange(cursorPosition + tabs))
        return true
    } else {
        return false
    }
}

private fun countLeadingTabs(input: String): Int {
    var count = 0
    for (char in input) {
        if (char == '\t' || char.isWhitespace()) {
            count++
        } else {
            break // Stop counting when a non-tab character is encountered
        }
    }
    return count
}

private fun appendClosingBrackets(textState: MutableState<TextFieldValue>, newValue: TextFieldValue): Boolean {
    val cursorPosition = newValue.selection.start
    val currentText = newValue.text

    val bracketsMap: Map<Char, Char> = mapOf(
        '(' to ')',
        '{' to '}',
        '[' to ']',
        '<' to '>',
        '\"' to '\"',
        '\'' to '\''
    )

    // Check if the last character entered is an opening bracket
    val openingBrackets = bracketsMap.keys
    val cursorIndex = maxOf(0, cursorPosition - 1)
    val lastSymbol = currentText[cursorIndex]
    if (currentText.length > textState.value.text.length && openingBrackets.contains(lastSymbol)) {
        // Insert a closing bracket at the current cursor position
        val closingBracket = bracketsMap[lastSymbol]!!
        val updatedText = currentText.insertCharAt(closingBracket, cursorPosition)

        // Set cursor position to be right after the opening bracket
        textState.value = TextFieldValue(updatedText, selection = TextRange(cursorPosition))
        return true
    } else {
        return false
    }
}