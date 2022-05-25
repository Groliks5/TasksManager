package com.groliks.tasksmanager.view.authentication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.groliks.tasksmanager.data.authenticaton.AuthenticationRepository
import com.groliks.tasksmanager.data.authenticaton.model.AuthenticationResponse
import com.groliks.tasksmanager.data.authenticaton.model.RegistrationResponse
import com.groliks.tasksmanager.data.authenticaton.model.User
import com.groliks.tasksmanager.data.util.RequestResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthenticationViewModel(
    private val repository: AuthenticationRepository
): ViewModel() {
    private val _registrationResponse: MutableSharedFlow<RequestResult<RegistrationResponse?>> = MutableSharedFlow()
    val registrationResponse = _registrationResponse.asSharedFlow()

    private val _authenticationResponse: MutableSharedFlow<RequestResult<AuthenticationResponse?>> = MutableSharedFlow()
    val authenticationResponse = _authenticationResponse.asSharedFlow()

    fun register(username: String, password: String) {
        viewModelScope.launch {
            val user = User(password, username)
            _registrationResponse.emit(repository.register(user))
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = User(password, username)
            _authenticationResponse.emit(repository.login(user))
        }
    }

    class Factory @Inject constructor(
        private val repository: AuthenticationRepository
    ): ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthenticationViewModel(repository) as T
        }
    }
}