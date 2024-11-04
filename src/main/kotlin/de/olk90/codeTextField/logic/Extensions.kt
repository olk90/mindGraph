package de.olk90.codeTextField.logic


fun String.insertCharAt(char: Char, index: Int): String {
    val sb = StringBuilder(this)
    sb.insert(index, char)
    return sb.toString()
}

fun String.insertStringAt(stringToInsert: String, index: Int): String {
    return when {
        index <= 0 -> stringToInsert + this
        index >= length -> this + stringToInsert
        else -> substring(0, index) + stringToInsert + substring(index)
    }
}

fun String.countNewlinesBefore(index: Int): Int {
    return this.substring(0, index.coerceAtMost(this.length) + 1).count { it == '\n' }
}