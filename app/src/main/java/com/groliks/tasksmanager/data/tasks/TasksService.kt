package com.groliks.tasksmanager.data.tasks

import com.groliks.tasksmanager.data.tasks.model.Task
import com.groliks.tasksmanager.data.tasks.model.TaskCompleteResponse
import com.groliks.tasksmanager.data.tasks.model.TaskResponse
import retrofit2.Response
import retrofit2.http.*

interface TasksService {
    @GET("/todo/tasks")
    suspend fun getTasks(
        @Header("Authorization") authToken: String,
        @Query("todo_list") listId: Int,
    ): Response<MutableList<TaskResponse>>

    @POST("/todo/tasks/")
    suspend fun createTask(
        @Header("Authorization") authToken: String,
        @Body task: Task
    ): Response<TaskResponse>

    @PUT("/todo/tasks/{id}/")
    suspend fun updateTask(
        @Header("Authorization") authToken: String,
        @Body task: Task,
        @Path("id") id: Int
    ): Response<TaskResponse>

    @DELETE("/todo/tasks/{id}/")
    suspend fun deleteTask(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): Response<String>

    @POST("/todo/tasks/complete/{id}/")
    suspend fun completeTask(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): Response<TaskCompleteResponse>
}