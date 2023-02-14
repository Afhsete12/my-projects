package com.example.todoappjetpack.addtask.ui

import com.example.todoappjetpack.addtask.ui.model.TaskModel

sealed interface TaskUiState {
    object Loading:TaskUiState
    data class Error(val throwable: Throwable):TaskUiState
    data class Success(val tasks:List<TaskModel>):TaskUiState
}