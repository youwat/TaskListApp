package com.example.watanabetakeshi.myapplication
import android.provider.BaseColumns
import java.util.Date

open class KBaseColumns {
    val _ID = "_id"
}
public class SqliteContract private constructor(){
    class MyTable private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            public val TABLE_NAME = "MyTable"
            public val COLUMN_NAME_INT_COL = "int_col"
            public val COLUMN_NAME_STR_COL = "str_col"
        }
    }
}
