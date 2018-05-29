package com.example.kangning.basecapsule.scan

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.TextView
import android.widget.EditText

class BackHandleEditText : EditText {

    private var listener: BackListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    interface BackListener {
        fun back(textView: TextView)
    }

    fun setBackListener(listener: BackListener) {
        this.listener = listener
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (listener != null) {
                listener!!.back(this)
            }
        }
        return false
    }
}