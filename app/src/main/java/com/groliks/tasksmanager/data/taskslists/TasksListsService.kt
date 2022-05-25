package com.groliks.tasksmanager.data.taskslists

import com.groliks.tasksmanager.data.taskslists.model.TasksList
import com.groliks.tasksmanager.data.taskslists.model.TasksListResponse
import retrofit2.Response
import retrofit2.http.*

interface TasksListsService {
    @GET("/todo/lists/")
    suspend fun getLists(
        @Header("Authorization") authToken: String
    ): Response<MutableList<TasksListResponse>>

    @POST("/todo/lists/")
    suspend fun createList(
        @Header("Authorization") authToken: String,
        @Body tasksList: TasksList
    ): Response<TasksListResponse>

    @PUT("/todo/lists/{id}/")
    suspend fun updateList(
        @Header("Authorization") authToken: String,
        @Body tasksList: TasksList,
        @Path("id") id: Int
    ): Response<TasksListResponse>

    @DELETE("/todo/lists/{id}/")
    suspend fun deleteList(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): Response<String>
}