package com.groliks.tasksmanager.data.tasks

import com.groliks.tasksmanager.data.tasks.model.Task
import com.groliks.tasksmanager.data.tasks.model.TaskCompleteResponse
import com.groliks.tasksmanager.data.tasks.model.TaskResponse
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.data.util.RequestResult

interface TasksRepository {
    suspend fun getTasks(authToken: String, listId: Int): RequestResult<MutableList<TaskResponse>?>
    suspend fun createTask(authToken: String, task: Task): RequestResult<TaskResponse?>
    suspend fun updateTask(authToken: String, task: Task, id: Int): RequestResult<TaskResponse?>
    suspend fun deleteTask(authToken: String, id: Int): RequestResult<String?>
    suspend fun completeTask(authToken: String, id: Int): RequestResult<TaskCompleteResponse?>
}