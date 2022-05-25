package com.groliks.tasksmanager.data.util

sealed class RequestResult<T> {
    class Success<T>(val data: T) : RequestResult<T>()
    class Error<T>(val msg: String): RequestResult<T>()
}