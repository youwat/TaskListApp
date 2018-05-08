package com.example.watanabetakeshi.myapplication.adapter

import android.graphics.Bitmap

data class TaskListViewItem(
        var id : Long,
        /** * チェックボックスの状態 */
        var checkBoxState: Boolean,
        /** * タスク名 */
        var name: String,
        /** １要素の高さ */
        var height : Int ?= null
)