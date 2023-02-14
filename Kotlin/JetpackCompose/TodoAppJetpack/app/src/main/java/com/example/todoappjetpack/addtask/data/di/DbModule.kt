package com.example.todoappjetpack.addtask.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoappjetpack.addtask.data.TaskDao
import com.example.todoappjetpack.addtask.data.TodoDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {


    @Provides
    fun provideTasksDao(todoDb: TodoDb):TaskDao{
        return todoDb.taskDao()
    }

    @Provides
    @Singleton
    fun provideTodoDb(@ApplicationContext appContext: Context):TodoDb{
        return Room.databaseBuilder(appContext,TodoDb::class.java,"TaskDb").build()
    }
}