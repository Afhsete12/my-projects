package com.example.todoappjetpack.addtask.domain

import com.example.todoappjetpack.addtask.data.TaskRepository
import com.example.todoappjetpack.addtask.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel){
        return taskRepository.add(taskModel)
    }
}