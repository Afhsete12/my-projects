package com.example.todoappjetpack.addtask.ui.model

import java.util.TimerTask

data class TaskModel(
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var isSelected: Boolean = false
)
