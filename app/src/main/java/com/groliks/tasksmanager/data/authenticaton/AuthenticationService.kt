package com.groliks.tasksmanager.data.authenticaton

import com.groliks.tasksmanager.data.authenticaton.model.AuthenticationResponse
import com.groliks.tasksmanager.data.authenticaton.model.RegistrationResponse
import com.groliks.tasksmanager.data.authenticaton.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("/accounts/authentication/reg/")
    suspend fun register(@Body user: User): Response<RegistrationResponse>

    @POST("/accounts/authentication/auth/")
    suspend fun login(@Body user: User): Response<AuthenticationResponse>
}