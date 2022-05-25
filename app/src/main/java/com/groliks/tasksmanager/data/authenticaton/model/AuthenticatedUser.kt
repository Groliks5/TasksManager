package com.groliks.tasksmanager.data.authenticaton.model

import com.google.gson.annotations.SerializedName

data class AuthenticatedUser(
    val username: String,
    val id: String,
    @SerializedName("is_active")
    val isActive: String,
)