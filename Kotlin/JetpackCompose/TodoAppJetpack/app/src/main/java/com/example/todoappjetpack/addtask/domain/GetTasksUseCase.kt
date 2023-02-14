package com.example.todoappjetpack.addtask.domain

import com.example.todoappjetpack.addtask.data.TaskRepository
import com.example.todoappjetpack.addtask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> =  taskRepository.tasks
}