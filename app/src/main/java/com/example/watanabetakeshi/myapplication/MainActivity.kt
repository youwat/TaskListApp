package com.example.watanabetakeshi.myapplication

import android.content.ContentValues
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.watanabetakeshi.myapplication.SqliteContract.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DB
        val mDbHelper = MyDbHelper(this);
        val reader = mDbHelper.readableDatabase
        val writer = mDbHelper.writableDatabase

        // INSERT
        // val values = ContentValues()
        // values.put(MyTable.COLUMN_NAME_INT_COL, 123)
        // values.put(MyTable.COLUMN_NAME_STR_COL, "aaa")
        // writer.insert(MyTable.TABLE_NAME, null, values)
        // values.put(MyTable.COLUMN_NAME_INT_COL, 234)
        // values.put(MyTable.COLUMN_NAME_STR_COL, "bbb")
        // writer.insert(MyTable.TABLE_NAME, null, values)

        // SELECT
        val projection = arrayOf(MyTable._ID,
                MyTable.COLUMN_NAME_INT_COL,
                MyTable.COLUMN_NAME_STR_COL
        )
        val selection = MyTable.COLUMN_NAME_INT_COL + " = ?" // Where句
        val selectionArgs = arrayOf("123")
        val sortOrder = MyTable.COLUMN_NAME_STR_COL + " DESC" // ORDER 句

        val cursor : Cursor = reader.query(
                MyTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        )
        while(cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(MyTable._ID))
            val str = cursor.getString(cursor.getColumnIndexOrThrow(MyTable.COLUMN_NAME_STR_COL))
            Log.d(TAG, "id : ${id} str : ${str}")
        }
        cursor.close()

        // DELETE
        val deleteSelection = MyTable._ID + " > ?" // WHERE 句
        val deleteSelectionArgs = arrayOf("5")
        writer.delete(MyTable.TABLE_NAME, deleteSelection, deleteSelectionArgs)

        // UPDATE
        val updateValues = ContentValues()
        updateValues.put(MyTable.COLUMN_NAME_STR_COL, "bbb")
        val updateSelection = MyTable.COLUMN_NAME_STR_COL + " = ?"
        val updateSelectionArgs = arrayOf("aaa")
        writer.update(
                MyTable.TABLE_NAME,
                updateValues,
                updateSelection,
                updateSelectionArgs)

        val listView = findViewById<ListView>(R.id.taskListView)
        listView.setOnItemClickListener {parent, view, position, id ->
            // 項目の TextView を取得
            // val checkBox : ImageView = view.findViewById(R.id.checkBoxImage)
            // checkBox.setImageResource(android.R.drawable.checkbox_on_background)
            Toast.makeText(this, "${id}", Toast.LENGTH_LONG).show()
        }
        // データの準備
        listView.emptyView = findViewById(R.id.emptyView)
        val taskList = TaskList(this, listView)

        // ボタン押したらListViewに追加
    }
}
