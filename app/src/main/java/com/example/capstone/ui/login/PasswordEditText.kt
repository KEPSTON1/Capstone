package com.example.capstone.ui.login

import android.content.Context
import android.util.AttributeSet
import com.example.capstone.R


class PasswordEditText : androidx.appcompat.widget.AppCompatEditText {

    private var isFocused = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (isFocused && text?.length!! < 8) {
            error = context.getString(R.string.warning)
        } else {
            error = null
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: android.graphics.Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        isFocused = focused
        if (focused && text?.length!! < 8) {
            error = context.getString(R.string.warning)
        } else {
            error = null
        }
    }
}