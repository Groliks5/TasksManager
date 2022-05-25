package com.groliks.tasksmanager.data.tasks.model

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    val id: Int,
    @SerializedName("_todo_list")
    val listName: String,
    @SerializedName("completed")
    var isCompleted: Boolean,
    var name: String,
    @SerializedName("todo_list")
    val list: Int,
)