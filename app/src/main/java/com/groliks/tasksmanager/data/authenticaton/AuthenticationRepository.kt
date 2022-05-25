package com.groliks.tasksmanager.data.authenticaton

import com.groliks.tasksmanager.data.authenticaton.model.AuthenticationResponse
import com.groliks.tasksmanager.data.authenticaton.model.RegistrationResponse
import com.groliks.tasksmanager.data.authenticaton.model.User
import com.groliks.tasksmanager.data.util.RequestResult

interface AuthenticationRepository {
    suspend fun register(user: User): RequestResult<RegistrationResponse?>
    suspend fun login(user: User): RequestResult<AuthenticationResponse?>
}