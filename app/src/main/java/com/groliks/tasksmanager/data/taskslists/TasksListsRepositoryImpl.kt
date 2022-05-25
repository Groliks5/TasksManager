package com.groliks.tasksmanager.data.taskslists

import com.groliks.tasksmanager.data.taskslists.model.TasksList
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.data.util.convertResponseToRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TasksListsRepositoryImpl @Inject constructor(
    private val tasksListsService: TasksListsService
) : TasksListsRepository {
    override suspend fun getLists(authToken: String): RequestResult<MutableList<TasksListResponse>?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksListsService.getLists(authToken)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun createList(
        authToken: String,
        tasksList: TasksList
    ): RequestResult<TasksListResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksListsService.createList(authToken, tasksList)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun updateList(
        authToken: String,
        tasksList: TasksList,
        id: Int
    ): RequestResult<TasksListResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksListsService.updateList(authToken, tasksList, id)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun deleteList(authToken: String, id: Int): RequestResult<String?> =
        withContext(Dispatchers.IO) {
            try {
                val response = tasksListsService.deleteList(authToken, id)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }
}