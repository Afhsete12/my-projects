package com.example.todoappjetpack.addtask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1)
abstract class TodoDb:RoomDatabase() {
    abstract fun taskDao():TaskDao
}