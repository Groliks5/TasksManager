package com.groliks.tasksmanager.data.taskslists

import com.groliks.tasksmanager.data.taskslists.model.TasksList
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.data.util.RequestResult

interface TasksListsRepository {
    suspend fun getLists(authToken: String): RequestResult<MutableList<TasksListResponse>?>
    suspend fun createList(authToken: String, tasksList: TasksList): RequestResult<TasksListResponse?>
    suspend fun updateList(authToken: String, tasksList: TasksList, id: Int): RequestResult<TasksListResponse?>
    suspend fun deleteList(authToken: String, id: Int): RequestResult<String?>
}