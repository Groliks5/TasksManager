package com.groliks.tasksmanager.view.taskslists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.groliks.tasksmanager.data.taskslists.TasksListsRepository
import com.groliks.tasksmanager.data.taskslists.model.TasksList
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.data.util.RequestResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksListsViewModel(
    private val repository: TasksListsRepository
) : ViewModel() {
    private var localTasksLists = mutableListOf<TasksListResponse>()
    private val _tasksLists =
        MutableSharedFlow<List<TasksListResponse>>(replay = 1)
    val tasksLists = _tasksLists.asSharedFlow()

    private val _errors = MutableSharedFlow<RequestResult<Any>>()
    val errors = _errors.asSharedFlow()

    fun loadTasksLists(authToken: String) {
        viewModelScope.launch {
            when (val requestResult = repository.getLists(authToken)) {
                is RequestResult.Success -> {
                    localTasksLists = requestResult.data ?: localTasksLists
                    _tasksLists.emit(localTasksLists)
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun createList(authToken: String, name: String) {
        viewModelScope.launch {
            when (val requestResult = repository.createList(authToken, TasksList(name))) {
                is RequestResult.Success -> {
                    requestResult.data?.also {
                        localTasksLists.add(it)
                        _tasksLists.emit(localTasksLists.toList())
                    }
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun updateList(authToken: String, name: String, id: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.updateList(authToken, TasksList(name), id)) {
                is RequestResult.Success -> {
                    requestResult.data?.also {
                        localTasksLists.find { it.id == requestResult.data.id }?.also {
                            val index = localTasksLists.indexOf(it)
                            localTasksLists[index] = requestResult.data
                        }
                        _tasksLists.emit(localTasksLists)
                    }
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    fun deleteList(authToken: String, id: Int) {
        viewModelScope.launch {
            when (val requestResult = repository.deleteList(authToken, id)) {
                is RequestResult.Success -> {
                    localTasksLists.find { it.id == id }?.also {
                        localTasksLists.remove(it)
                    }
                    _tasksLists.emit(localTasksLists)
                }
                is RequestResult.Error -> {
                    _errors.emit(RequestResult.Error(requestResult.msg))
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val repository: TasksListsRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TasksListsViewModel(repository) as T
        }
    }
}