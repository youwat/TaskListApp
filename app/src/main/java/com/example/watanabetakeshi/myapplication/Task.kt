package com.example.watanabetakeshi.myapplication

import java.util.Date

class Task(
        var id: Int?,
        var title: String,
        var body: String,
        var contextId: Int ?= null,
        var tagIdList: Set<Int> ?= null,
        var startDate: Date ?= null,
        var dueDate: Date ?= null)
