package com.groliks.tasksmanager.data.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.groliks.tasksmanager.data.authenticaton.model.RegistrationErrorResponse
import retrofit2.Response

fun <T>convertResponseToRequestResult(response: Response<T>): RequestResult<T?> {
    return if (response.isSuccessful) {
        RequestResult.Success(response.body())
    } else {
        val type = object : TypeToken<RegistrationErrorResponse>() {}.type
        try {
            val errorResponse: RegistrationErrorResponse? =
                Gson().fromJson(response.errorBody()!!.charStream(), type)
            RequestResult.Error(errorResponse!!.detail)
        } catch (e: Exception) {
            RequestResult.Error(response.message())
        }
    }
}