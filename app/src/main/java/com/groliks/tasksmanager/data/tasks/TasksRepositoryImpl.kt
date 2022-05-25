package com.groliks.tasksmanager.data.tasks

import com.groliks.tasksmanager.data.tasks.model.Task
import com.groliks.tasksmanager.data.tasks.model.TaskCompleteResponse
import com.groliks.tasksmanager.data.tasks.model.TaskResponse
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.data.util.convertResponseToRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksService: TasksService
) : TasksRepository {
    override suspend fun getTasks(
        authToken: String,
        listId: Int
    ): RequestResult<MutableList<TaskResponse>?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksService.getTasks(authToken, listId)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun createTask(
        authToken: String,
        task: Task
    ): RequestResult<TaskResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksService.createTask(authToken, task)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun updateTask(
        authToken: String,
        task: Task,
        id: Int
    ): RequestResult<TaskResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksService.updateTask(authToken, task, id)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun deleteTask(authToken: String, id: Int): RequestResult<String?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksService.deleteTask(authToken, id)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun completeTask(
        authToken: String,
        id: Int
    ): RequestResult<TaskCompleteResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksService.completeTask(authToken, id)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }
}