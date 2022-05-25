package com.groliks.tasksmanager.data.taskslists.model

import com.google.gson.annotations.SerializedName

data class TasksListResponse(
    val id: Int,
    @SerializedName("completed")
    val isCompleted: Boolean,
    @SerializedName("completion_progress")
    val completionProgress: Float,
    var name: String,
)