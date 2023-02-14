package com.example.todoappjetpack.addtask.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappjetpack.addtask.domain.AddTaskUseCase
import com.example.todoappjetpack.addtask.domain.DeleteTaskUseCase
import com.example.todoappjetpack.addtask.domain.GetTasksUseCase
import com.example.todoappjetpack.addtask.domain.UpdateTaskUseCase
import com.example.todoappjetpack.addtask.ui.TaskUiState.Loading
import com.example.todoappjetpack.addtask.ui.TaskUiState.Success
import com.example.todoappjetpack.addtask.ui.TaskUiState.Error
import com.example.todoappjetpack.addtask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val addTaskUpdateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> =
        getTasksUseCase()
            .map(::Success)
            .catch { Error(it) }
            .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), Loading
        )

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    //private val _tasks = mutableStateListOf<TaskModel>()
    //val tasks: List<TaskModel> = _tasks


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun showDialogClicked() {
        _showDialog.value = true
    }

    fun onTaskSaved(task: String) {
        _showDialog.value = false
        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            addTaskUpdateTaskUseCase(taskModel.copy(isSelected = !taskModel.isSelected))
        }

    }

    fun onItemRemoved(taskModel: TaskModel) {
        viewModelScope.launch {
            deleteTaskUseCase(taskModel.copy(id = taskModel.id))
        }
        //val task = _tasks.find { it.id == taskModel.id }
        //_tasks.remove(task)

    }


}