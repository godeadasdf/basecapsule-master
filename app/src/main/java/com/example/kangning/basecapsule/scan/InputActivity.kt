package com.example.kangning.basecapsule.scan

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager

import kotlinx.android.synthetic.main.activity_input.*
import android.content.Intent
import android.view.KeyEvent
import android.widget.TextView
import com.example.kangning.basecapsule.R


class InputActivity : AppCompatActivity() {

    companion object {
        const val SUMMIT_MESSAGE = "submit_message"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        supportActionBar?.hide()
        input_edit.setBackListener(object : BackHandleEditText.BackListener {
            override fun back(textView: TextView) {
                finish()
            }
        })
        submit.setOnClickListener {
            val mIntent = Intent()
            mIntent.putExtra(SUMMIT_MESSAGE, input_edit.text.toString())
            this.setResult(0, mIntent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        showSoftInputFromWindow(true)
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    private fun showSoftInputFromWindow(focusable: Boolean) {
        input_edit.isFocusable = focusable
        input_edit.isFocusableInTouchMode = focusable
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (focusable === true) {
            input_edit.requestFocus()
            imm.showSoftInput(input_edit, InputMethodManager.SHOW_IMPLICIT)
        } else {
            imm.hideSoftInputFromWindow(input_edit.windowToken, 0)
        }
    }

    override fun onBackPressed() {
        finish()
    }

}

