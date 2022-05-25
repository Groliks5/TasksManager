package com.groliks.tasksmanager.di.data

import com.groliks.tasksmanager.BuildConfig
import com.groliks.tasksmanager.di.data.authentication.AuthenticationDataModule
import com.groliks.tasksmanager.di.data.tasks.TasksDataModule
import com.groliks.tasksmanager.di.data.taskslists.TasksListsDataModule
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [AuthenticationDataModule::class, TasksListsDataModule::class, TasksDataModule::class])
class DataModule {
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}