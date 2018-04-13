package com.example.watanabetakeshi.myapplication
import java.util.Date

data class Task (
        var id: Int?,
        var taskName: String,
        var contextId: Int = 0,
        var startDate: Date = Date(),
        var dueDate: Date = Date(),
        var endDate: Date = Date(),
        var playTime: Int ?= null
)
