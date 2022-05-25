package com.groliks.tasksmanager.data.authenticaton.model

data class AuthenticationResponse(
    val token: String,
    val user: AuthenticatedUser,
)