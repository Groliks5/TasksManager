package com.groliks.tasksmanager.data.authenticaton

import com.groliks.tasksmanager.data.authenticaton.model.AuthenticationResponse
import com.groliks.tasksmanager.data.authenticaton.model.RegistrationResponse
import com.groliks.tasksmanager.data.authenticaton.model.User
import com.groliks.tasksmanager.data.util.RequestResult
import com.groliks.tasksmanager.data.util.convertResponseToRequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
) : AuthenticationRepository {
    override suspend fun register(user: User): RequestResult<RegistrationResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = authenticationService.register(user)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }

    override suspend fun login(user: User): RequestResult<AuthenticationResponse?> =
        withContext(Dispatchers.IO) {
            try {
                val response = authenticationService.login(user)
                convertResponseToRequestResult(response)
            } catch (e: Exception) {
                RequestResult.Error(e.message.toString())
            }
        }
}