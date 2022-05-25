package com.groliks.tasksmanager.view.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.groliks.tasksmanager.data.tasks.TasksRepository
import com.groliks.tasksmanager.data.tasks.model.Task
import com.groliks.tasksmanager.data.tasks.model.TaskResponse
import com.groliks.tasksmanager.data.util.RequestResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskListViewModel(
    private val repository: TasksRepository
) : ViewModel() {
    private var localTasks = mutableListOf<TaskResponse>()
    private val _tasks = MutableSharedFlow<List<TaskResponse>>(replay = 1)
    val tasks = _tasks.asSharedFlow()

    private val _errors = MutableSharedFlow<RequestResult<Any>>()
    val errors = _errors.asSharedFlow()

    fun loadTasks(authToken: String, listId: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.getTasks(authToken, listId)) {
                is RequestResult.Success -> {
                    localTasks = requestResult.data ?: localTasks
                    _tasks.emit(localTasks)
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun createTask(authToken: String, name: String, listId: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.createTask(authToken, Task(listId, name))) {
                is RequestResult.Success -> {
                    requestResult.data?.also {
                        localTasks.add(it)
                        _tasks.emit(localTasks)
                    }
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun updateTask(authToken: String, name: String, id: Int, listId: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.updateTask(authToken, Task(listId, name), id)) {
                is RequestResult.Success -> {
                    requestResult.data?.also {
                        localTasks.find { it.id == requestResult.data.id }?.also {
                            val index = localTasks.indexOf(it)
                            localTasks[index] = requestResult.data
                        }
                        _tasks.emit(localTasks)
                    }
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun deleteTask(authToken: String, id: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.deleteTask(authToken, id)) {
                is RequestResult.Success -> {
                    localTasks.find { it.id == id }?.also {
                        localTasks.remove(it)
                    }
                    _tasks.emit(localTasks)
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun completeTask(authToken: String, id: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.completeTask(authToken, id)) {
                is RequestResult.Success -> {
                    requestResult.data?.also {
                        localTasks.find { it.id == id }?.also {
                            it.isCompleted = true
                        }
                        _tasks.emit(localTasks)
                    }
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: TasksRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskListViewModel(repository) as T
        }
    }
}