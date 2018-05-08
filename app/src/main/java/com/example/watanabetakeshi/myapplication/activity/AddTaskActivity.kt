package com.example.watanabetakeshi.myapplication.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.watanabetakeshi.myapplication.R
import com.example.watanabetakeshi.myapplication.Tasks
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.KeyEvent
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager


class AddTaskActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // イベント
        val editText = findViewById<Button>(R.id.add_TaskButton)
        editText.setOnClickListener {
            val name = findViewById<EditText>(R.id.add_TaskName).text.toString()
            val sendIntent = Intent()
            sendIntent.putExtra("taskName", name)

            setResult(RESULT_OK, sendIntent)
            finish()
        }
//        editText.requestFocus()
//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }
}
