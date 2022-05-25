package com.groliks.tasksmanager.data.tasks.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("todo_list")
    val list: Int,
    var name: String
)