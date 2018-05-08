package com.example.watanabetakeshi.myapplication.contract
import android.provider.BaseColumns

abstract class KBaseColumns {
    abstract val TABLE_NAME: String
    val _ID = "_id"
    val CREATED_AT = "created_at"
    val UPDATE_AT = "update_at"
}

public class SqliteContract private constructor(){
    class TaskTable private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            override val TABLE_NAME = "Task"
            val NAME = "name"
            val NOTE = "note"
            val IS_CHECKED = "is_checked"
        }
    }
    class TagConnectionTable private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            override val TABLE_NAME = "tag_connection"
            val TASK_TABLE_ID = "task_table_id"
            val TAG_TABLE_ID = "tag_table_id"
        }
    }
    class TagTable private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            override val TABLE_NAME = "tag"
            val NAME = "name"
        }
    }
    class ContextConnectionTable private constructor(): BaseColumns {
        companion object : KBaseColumns(){
            override val TABLE_NAME = "context_connection"
            val TASK_TABLE_ID = "task_table_id"
            val CONTEXT_TABLE_ID = "context_table_id"
        }
    }
    class ContextTable private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            override val TABLE_NAME = "context"
            val NAME = "nmae"
        }
    }
}
