package com.maxpay.sdk.ui.view.input

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

internal class MaskTextWatcher(
    private val editText: TextInputEditText,
    private val mask: String,
    private val prefix: String = ""
) : TextWatcher {

    private var selfChange = false
    private var deleting = false
    private var past = false
    private var deletingChar: Char? = null

    override fun afterTextChanged(editable: Editable) {
        if (selfChange) return
        selfChange = true
        format(
            when {
                deleting && past -> {
                    editable.clear()
                    editable.append(prefix)
                }
                deleting && deletingChar?.isLetterOrDigit() == false -> {
                    val unformat = replacePlaceholders(editable.toString())
                    if (unformat.length >= prefix.length) {
                        val editableFilters = editable.filters
                        editable.filters = emptyArray()
                        editable.replace(0, editable.length, removeLastLetterOrDigit(unformat)).also {
                            editable.filters = editableFilters
                        }
                    } else editable
                }
                else -> editable
            }
        )
        selfChange = false
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        deleting = count > after && start > 0
        past = count > 1
        if (deleting) deletingChar = s[start]
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    private fun format(text: Editable) {
        text.run {
            // reset input filters
            val editableFilters = filters
            filters = emptyArray()

            val formatted = StringBuilder()
            val list = map { it.toUpperCase() }.toMutableList()

            // apply mask
            mask.forEach { m ->
                var c = list.firstOrNull() ?: placeholder
                if (isPlaceHolder(m)) {
                    if (!c.isLetterOrDigit() && !isPlaceHolder(c)) {
                        // find next letter or digit
                        val iterator = list.iterator()
                        while (iterator.hasNext()) {
                            c = iterator.next()
                            if (c.isLetterOrDigit()) break
                            iterator.remove()
                        }
                    }
                    formatted.append(c)
                    if (list.isNotEmpty()) {
                        list.removeAt(0)
                    }
                } else {
                    formatted.append(m)
                    if (m == c && this.length > 1) {
                        list.removeAt(0)
                    }
                }
            }
            val previousLength = length
            val currentLength = formatted.length
            replace(0, previousLength, formatted, 0, currentLength)

            findCursorPosition(text, editText.selectionStart)
            // restore input filters
            filters = editableFilters
        }
    }

    fun unformat(text: Editable?): String? {
        if (text.isNullOrEmpty()) return null
        val unformatted = StringBuilder()
        val textLength = text.length
        mask.forEachIndexed { index, m ->
            if (index >= textLength) return@forEachIndexed
            if (isPlaceHolder(m)) {
                unformatted.append(text[index])
            }
        }
        return unformatted.toString()
    }

    fun replacePlaceholders(text: String): String {
        if (text.isEmpty()) return ""
        val unformatted = StringBuilder()
        for (char in text) {
            if (!isPlaceHolder(char)) {
                unformatted.append(char)
            } else break
        }
        return unformatted.toString()
    }

    fun findCursorPosition(text: Editable?, start: Int): Int {
        if (text.isNullOrEmpty()) return start
        val textLength = text.length
        var position = 0
        for (i in 0 until textLength) {
            if (isPlaceHolder(text[i])) {
                break
            }
            position++
        }
        val currentSelection = if (position < textLength) position else textLength
        editText.setSelection(currentSelection)
        return currentSelection
    }

    private fun removeLastLetterOrDigit(string: String): String {
        if (string.isEmpty())
            return string
        var str = string
        while (!str.last().isLetterOrDigit()) {
            str = str.removeRange(str.length - 1, str.length)
        }
        return str.removeRange(str.length - 1, str.length)
    }

    private fun isPlaceHolder(char: Char) = char == placeholder

    companion object {
        const val placeholder = '_'
    }
}