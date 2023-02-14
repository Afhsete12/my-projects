package com.example.todoappjetpack.addtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Long = System.currentTimeMillis(),
    val task: String,
    var isSelected: Boolean = false
)