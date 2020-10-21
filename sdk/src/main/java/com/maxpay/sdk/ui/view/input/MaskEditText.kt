package com.maxpay.sdk.ui.view.input

import android.content.Context
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.maxpay.sdk.R

class MaskEditText : TextInputEditText {

    private var maskTextWatcher: MaskTextWatcher? = null

    var mask: String? = null
        set(value) {
            field = value
            value?.let { mask ->
                maskTextWatcher = MaskTextWatcher(this, mask, prefix)
                addTextChangedListener(maskTextWatcher)
            } ?: removeTextChangedListener(maskTextWatcher)
        }

    var prefix: String = ""
        set(value) {
            field = value
            mask = mask
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.MaskEditText)
            with(a) {
                prefix = getString(R.styleable.MaskEditText_edit_text_prefix) ?: ""
                mask = getString(R.styleable.MaskEditText_edit_text_mask)
                recycle()
            }
        }

        setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (v as TextInputEditText).let {
                    if (it.text?.length == 0) {
                        it.setText(prefix)
                        it.setSelection(it.text?.length ?: 0)
                    }
                }
                maskTextWatcher?.findCursorPosition(v.editableText, v.selectionStart)
            }
        }

        setOnClickListener {
            val editText = it as EditText
            maskTextWatcher?.findCursorPosition(editText.editableText, editText.selectionStart)
        }

        customSelectionActionModeCallback = object : ActionMode.Callback {

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}

            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                maskTextWatcher?.findCursorPosition(this@MaskEditText.editableText, this@MaskEditText.selectionStart)
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        }
    }
}